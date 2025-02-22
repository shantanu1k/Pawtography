package com.cowday.pawtography.data

import com.cowday.pawtography.database.DogDatabase
import com.cowday.pawtography.models.local.DogEntity
import com.cowday.pawtography.models.network.DogResponse
import com.cowday.pawtography.network.DogApi
import retrofit2.Response

class DogRepository(
    private val api: DogApi,
    dogDatabase: DogDatabase,
) {
    private val dogDao = dogDatabase.dogDao()

    suspend fun getDogImage(): Response<DogResponse> {
        return api.getRandomDogImage()
    }

    fun getRecentDogRecords() = dogDao.getRecentDogRecords()

    suspend fun insertDogRecord(dogEntity: DogEntity) {
        dogDao.insertDogRecord(dogEntity)
    }

    suspend fun deleteRecentDogRecord() {
        dogDao.deleteRecentDogRecord()
    }

    suspend fun deleteAllDogRecords() {
        dogDao.deleteAllDogRecords()
    }
}