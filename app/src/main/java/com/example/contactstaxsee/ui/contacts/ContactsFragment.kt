package com.example.contactstaxsee.ui.contacts

import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.contactstaxsee.R
import com.example.contactstaxsee.data.model.Department
import com.example.contactstaxsee.data.model.DepartmentItem
import com.example.contactstaxsee.data.model.Employee

class ContactsFragment : Fragment() {

    companion object {
        fun newInstance() = ContactsFragment()
    }

    private lateinit var viewModel: ContactsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var contactsAdapter: ContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.contacts_fragment, container, false)
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)

        recyclerView = view.findViewById(R.id.rv_employees)
        contactsAdapter = ContactsAdapter(mutableListOf())
        recyclerView.apply {
            adapter = contactsAdapter
            setHasFixedSize(true)
        }

        val pref = activity?.getPreferences(MODE_PRIVATE)
        val login = pref?.getString("login", "no_login") ?: "no_pref"
        val password = pref?.getString("password", "no_password") ?: "no_pref"
        viewModel.loadEmployees(login, password)
        viewModel.contacts.observe(this as LifecycleOwner, Observer {
            showEmployees(it)
        })

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.logout_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_logout) {
            deleteUser()
            val navController = findNavController()
            navController.popBackStack()
            navController.navigate(R.id.authFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    fun deleteUser() {
        val pref = activity?.getPreferences(MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.remove("login")
        editor?.remove("password")
        editor?.apply()
    }

    fun prepare(department: DepartmentItem, level: Int) {
        department.level = level
        if (department is Department) {
            department.departments?.forEach {
                prepare(it as DepartmentItem, level + 1)
            }
            department.employees?.forEach {
                prepare(it as DepartmentItem, level + 1)
            }
        }
    }

    fun showEmployees(departments: MutableList<Department>) {
        departments.forEach{
            prepare(it as DepartmentItem, 0)
        }
        contactsAdapter.employees.clear()
        contactsAdapter.employees.addAll(departments)
        contactsAdapter.notifyDataSetChanged()
    }

}