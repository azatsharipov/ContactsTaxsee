package com.example.contactstaxsee.data.model

import android.os.Parcel
import android.os.Parcelable

class User (
    val login: String,
    val password: String,
    var photo: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString() as String,
            parcel.readString() as String,
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(login)
        parcel.writeString(password)
        parcel.writeString(photo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}