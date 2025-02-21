package com.cowday.pawtography.data

import com.cowday.pawtography.models.network.DogResponse
import com.cowday.pawtography.network.DogApi
import retrofit2.Response

class DogRepository(
    private val api: DogApi
) {
    suspend fun getDogImage(): Response<DogResponse> {
        return api.getRandomDogImage()
    }
}