package com.example.contactstaxsee.data.repository

import com.example.contactstaxsee.data.api.ContactsApiFactory
import com.example.contactstaxsee.data.model.AuthResponse
import com.example.contactstaxsee.data.model.ContactsResponse
import retrofit2.Response

class ContactsRepository {
    suspend fun auth(login: String, password: String): Response<AuthResponse> {
        val api = ContactsApiFactory.api
        val result = api.auth(login, password).await()
        return result
    }

    suspend fun getAll(login: String, password: String): Response<ContactsResponse> {
        val api = ContactsApiFactory.api
        val result = api.getAll(login, password).await()
        return result
    }

    suspend fun getPhoto(login: String, password: String, id: String): Response<String> {
        val api = ContactsApiFactory.api
        val result = api.getPhoto(login, password, id).await()
        return result
    }
}