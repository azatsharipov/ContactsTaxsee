package com.example.contactstaxsee.ui.employee

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactstaxsee.data.repository.ContactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class EmployeeViewModel : ViewModel() {

    val photo = MutableLiveData<String>()

    fun loadPhoto(login: String, password: String, id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = ContactsRepository().getPhoto(login, password, id)
                if (result.isSuccessful) {
                    photo.postValue(result.body())
                } else {
                    // error
                }
            } catch (e: Exception) {
                // error
            }
        }
    }
}