package com.example.contactstaxsee.data.model

import com.google.gson.annotations.SerializedName

class AuthResponse (
    @SerializedName("Message")
    val message: String,
    @SerializedName("Success")
    val isSuccess: Boolean
)