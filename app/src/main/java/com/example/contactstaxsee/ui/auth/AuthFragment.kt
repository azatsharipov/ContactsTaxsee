package com.example.contactstaxsee.ui.auth

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.contactstaxsee.R

class AuthFragment : Fragment() {

    companion object {
        fun newInstance() = AuthFragment()
    }

    private lateinit var viewModel: AuthViewModel
    private lateinit var etLogin: EditText
    private lateinit var etPassword: EditText
    private lateinit var btAuth: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.auth_fragment, container, false)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        etLogin = view.findViewById(R.id.et_auth_login)
        etPassword = view.findViewById(R.id.et_auth_password)
        btAuth = view.findViewById(R.id.bt_auth)

        btAuth.setOnClickListener {
            viewModel.auth(etLogin.text.toString(), etPassword.text.toString())
        }

        val pref = activity?.getPreferences(MODE_PRIVATE)
        if (pref?.contains("login") ?: false) {
            openContacts()
        }
        viewModel.user.observe(this as LifecycleOwner, Observer {
            saveUser(it)
            openContacts()
        })
        return view
    }

    fun saveUser(user: Pair<String, String>) {
        val pref = activity?.getPreferences(MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.putString("login", user.first)
        editor?.putString("password", user.second)
        editor?.apply()
    }

    fun openContacts() {
        val view = activity?.currentFocus
        val inputManager: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
        findNavController().popBackStack()
        findNavController().navigate(R.id.contactsFragment)
    }

}