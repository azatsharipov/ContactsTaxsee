package com.example.contactstaxsee.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactstaxsee.data.repository.ContactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    val user = MutableLiveData<Pair<String, String>>()

    fun auth(login: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = ContactsRepository().auth(login, password)
                if (result.isSuccessful) {
                    if (result.body()?.isSuccess == true) {
                        user.postValue(Pair(login, password))
                    } else {
                        // success = false
                    }
                } else {
                    // error
                }
            } catch (e: Exception) {
                // error
            }

        }
    }

}