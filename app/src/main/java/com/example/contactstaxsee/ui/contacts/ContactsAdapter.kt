package com.example.contactstaxsee.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.contactstaxsee.R
import com.example.contactstaxsee.data.model.Department
import com.example.contactstaxsee.data.model.DepartmentItem
import com.example.contactstaxsee.data.model.Employee

class ContactsAdapter(var employees: MutableList<DepartmentItem>) :
    RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    class ContactsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_employee_item_name)
        val ivTree: ImageView = view.findViewById(R.id.iv_employee_item_tree)
        var opened = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.employee_item, parent, false) as View
        return ContactsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return employees.size
    }

    fun hideEmployees(emp: DepartmentItem) {
        val emps = (emp as Department).departments
                ?: (emp as Department).employees
                ?: mutableListOf()
        employees.removeAll(emps)
        emps.forEach {
            if (it is Department)
                hideEmployees(it)
        }
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.tvName.setText(employees[position].name)
        holder.itemView.setPadding(employees[position].level * 50, 0, 0, 0)
        if (employees[position] is Department) {
            holder.ivTree.setImageResource(R.drawable.ic_baseline_arrow_right_24)
            holder.ivTree.visibility = View.VISIBLE
            holder.itemView.setOnClickListener {
                val position = holder.position
                val oldSize = employees.size
                if (holder.opened) {
                    holder.ivTree.setImageResource(R.drawable.ic_baseline_arrow_right_24)
                    holder.opened = false
                    hideEmployees(employees[position])
                    notifyItemRangeRemoved(position + 1, oldSize - employees.size)
                } else {
                    holder.ivTree.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
                    holder.opened = true
                    employees.addAll(
                            position + 1,
                            (employees[position] as Department).departments
                                    ?: (employees[position] as Department).employees
                                    ?: mutableListOf())
                    notifyItemRangeInserted(position + 1, employees.size - oldSize)
                }
            }
        } else if (employees[position] is Employee) {
            holder.ivTree.visibility = View.GONE
            holder.itemView.setOnClickListener {
                val navController = it.findNavController()
                val bundle = Bundle()
                bundle.putParcelable("employee", employees[holder.position] as Employee)
                navController.navigate(R.id.action_contactsFragment_to_employeeFragment, bundle)
            }
        }
    }
}