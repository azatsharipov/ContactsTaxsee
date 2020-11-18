package com.example.contactstaxsee.data.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ContactsApiFactory {
    fun retrofit() : Retrofit = Retrofit.Builder()
        .baseUrl("https://contact.taxsee.com/Contacts.svc/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()


    val api : ContactsApi = retrofit().create(ContactsApi::class.java)
}