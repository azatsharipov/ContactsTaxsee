package com.example.contactstaxsee.ui.contacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactstaxsee.data.model.Department
import com.example.contactstaxsee.data.model.Employee
import com.example.contactstaxsee.data.repository.ContactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactsViewModel : ViewModel() {

    val contacts = MutableLiveData<MutableList<Department>>()

    fun loadEmployees(login: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = ContactsRepository().getAll(login, password)
                if (result.isSuccessful) {
                    contacts.postValue(result.body()?.departments)
                } else {
                    // error
                }
            } catch (e: Exception) {
                // error
            }

        }
    }
}