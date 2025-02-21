package com.cowday.pawtography.network

import com.cowday.pawtography.models.network.DogResponse
import retrofit2.http.GET

interface DogApi {
    @GET("api/breeds/image/random")
    suspend fun getRandomDogImage(): DogResponse
}