package com.cowday.pawtography

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.cowday.pawtography.data.DogRepository
import com.cowday.pawtography.models.network.DogResponse
import com.cowday.pawtography.models.network.asEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainViewModel(
    private val dogRepository: DogRepository,
    private val imageLoader: ImageLoader? = null,
): ViewModel() {
    private val _generateDogsScreenState = MutableStateFlow<GenerateDogsScreenState>(
        GenerateDogsScreenState.Initial
    )
    val generateDogsScreenState = _generateDogsScreenState.asStateFlow()

    fun getDogImage() {
        _generateDogsScreenState.value = GenerateDogsScreenState.Loading
        viewModelScope.launch {
            try {
                val response = dogRepository.getDogImage()
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.message == null) {
                        _generateDogsScreenState.value = GenerateDogsScreenState.Error()
                    } else {
                        updateDogTable(response.body()!!)
                        _generateDogsScreenState.value = GenerateDogsScreenState.Success(
                            response.body()!!.message!!
                        )
                    }
                }
                else {
                    _generateDogsScreenState.value = GenerateDogsScreenState.Error()
                }
            } catch (e: Exception) {
                _generateDogsScreenState.value = GenerateDogsScreenState.Error(e.message)
            }
        }
    }

    fun getRecentDogRecords() = dogRepository.getRecentDogRecords()

    @OptIn(ExperimentalCoilApi::class)
    fun deleteAllDogRecords() {
        viewModelScope.launch {
            dogRepository.deleteAllDogRecords()
            val recentRecords = dogRepository.getRecentDogRecords().firstOrNull() ?: return@launch
            recentRecords.forEach {
                imageLoader?.diskCache?.remove(it.imageUrl)
            }
        }
    }

    @OptIn(ExperimentalCoilApi::class)
    private suspend fun updateDogTable(dogResponse: DogResponse) {
        val recentRecords = dogRepository.getRecentDogRecords().firstOrNull() ?: return
        val oldestRecord = recentRecords.lastOrNull() ?: run {
            dogRepository.insertDogRecord(dogResponse.asEntity())
            return
        }

        if (recentRecords.size >= DOG_RECORDS_LIMIT) {
            imageLoader?.diskCache?.remove(oldestRecord.imageUrl)
            dogRepository.deleteRecentDogRecord()
        }
        dogRepository.insertDogRecord(dogResponse.asEntity())
    }

    class MainViewModelFactory(
        private val dogRepository: DogRepository,
        private val imageLoader: ImageLoader? = null
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(
                    dogRepository, imageLoader
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    sealed class GenerateDogsScreenState {
        data object Initial: GenerateDogsScreenState()
        data object Loading: GenerateDogsScreenState()
        data class Success(val imageUrl: String): GenerateDogsScreenState()
        data class Error(val message: String? = null): GenerateDogsScreenState()
    }

    companion object {
        private const val DOG_RECORDS_LIMIT = 20
    }
}