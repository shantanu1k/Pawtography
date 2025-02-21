package com.cowday.pawtography

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cowday.pawtography.data.DogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val dogRepository: DogRepository
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
                        _generateDogsScreenState.value = GenerateDogsScreenState.Success(
                            response.body()!!.message!!
                        )
                    }
                }
            } catch (e: Exception) {
                _generateDogsScreenState.value = GenerateDogsScreenState.Error(e.message)
            }
        }
    }

    class MainViewModelFactory(
        private val dogRepository: DogRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(
                    dogRepository
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
}