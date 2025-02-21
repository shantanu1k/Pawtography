package com.cowday.pawtography.models.network

import com.google.gson.annotations.SerializedName

data class DogResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: RequestStatus? = null,
    @SerializedName("code")
    val requestCode: Int? = null,
)

enum class RequestStatus(val value: String) {
    SUCCESS("success"), ERROR("error");
}
