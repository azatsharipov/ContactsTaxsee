package com.example.contactstaxsee.data.api

import com.example.contactstaxsee.data.model.AuthResponse
import com.example.contactstaxsee.data.model.ContactsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ContactsApi {
    @GET("Hello")
    fun auth(
        @Query("login") login: String,
        @Query("password") password: String
    ): Deferred<Response<AuthResponse>>

    @GET("GetAll")
    fun getAll(
        @Query("login") login: String,
        @Query("password") password: String
    ): Deferred<Response<ContactsResponse>>

    @GET("GetWPhoto")
    fun getPhoto(
        @Query("login") login: String,
        @Query("password") password: String,
        @Query("id") id: String
    ): Deferred<Response<String>>
}