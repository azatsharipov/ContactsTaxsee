package com.example.contactstaxsee.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

abstract class DepartmentItem (
    @SerializedName("ID")
    val id: String,
    @SerializedName("Name")
    val name: String,
    var level: Int = 0
)

class ContactsResponse (
    id: String,
    name: String,
    @SerializedName("Departments")
    val departments: MutableList<Department>
) : DepartmentItem(id, name)

class Department (
    id : String,
    name : String,
    @SerializedName("Employees")
    val employees: MutableList<Employee>?,
    @SerializedName("Departments")
    val departments: MutableList<Department>?
) : DepartmentItem(id, name)

class Employee (
    id: String,
    name: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Email")
    val email: String?,
    @SerializedName("Phone")
    val phone: String?
) : DepartmentItem(id, name), Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString() as String,
            parcel.readString() as String,
            parcel.readString() as String,
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(title)
        parcel.writeString(email)
        parcel.writeString(phone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Employee> {
        override fun createFromParcel(parcel: Parcel): Employee {
            return Employee(parcel)
        }

        override fun newArray(size: Int): Array<Employee?> {
            return arrayOfNulls(size)
        }
    }
}