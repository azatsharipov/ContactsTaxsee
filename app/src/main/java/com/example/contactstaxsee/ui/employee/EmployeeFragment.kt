package com.example.contactstaxsee.ui.employee

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.contactstaxsee.R
import com.example.contactstaxsee.data.model.Employee


class EmployeeFragment : Fragment() {

    companion object {
        fun newInstance() =
            EmployeeFragment()
    }

    private lateinit var viewModel: EmployeeViewModel
    private lateinit var tvName: TextView
    private lateinit var tvTitle: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvEmail: TextView
    private lateinit var ivPhoto: ImageView
    private var employee: Employee? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.employee_fragment, container, false)
        viewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)

        tvName = view.findViewById(R.id.tv_employee_name)
        tvTitle = view.findViewById(R.id.tv_employee_title)
        tvPhone = view.findViewById(R.id.tv_employee_phone)
        tvEmail = view.findViewById(R.id.tv_employee_email)
        ivPhoto = view.findViewById(R.id.iv_employee_photo)

        val pref = activity?.getPreferences(Context.MODE_PRIVATE)
        val login = pref?.getString("login", "no_login") ?: "no_pref"
        val password = pref?.getString("password", "no_password") ?: "no_pref"

        tvPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "${tvPhone.text}"))
            startActivity(intent)
        }

        tvEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto","abc@gmail.com", null));
            startActivity(Intent.createChooser(intent, "Send email"))
        }

        employee = arguments?.getParcelable<Employee>("employee")
        employee?.let {
            showEmployee(it)
        }
        employee?.id?.let {
            viewModel.loadPhoto(login, password, it)
        }

        viewModel.photo.observe(this as LifecycleOwner, Observer {
            showPhoto(it)
        })

        return view
    }

    fun showEmployee(employee: Employee) {
        tvName.setText(employee.name)
        tvTitle.setText(employee.title)
        tvPhone.setText(employee.phone)
        tvEmail.setText(employee.email)
    }

    fun showPhoto(photo: String) {
        Glide.with(this).load(photo).into(ivPhoto)
    }

}