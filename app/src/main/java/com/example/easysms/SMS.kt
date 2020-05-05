package com.example.easysms

import android.os.Parcel
import android.os.Parcelable

class SMS(val fromAddress: String, val body: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(fromAddress)
        dest?.writeString(body)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SMS> {
        override fun createFromParcel(parcel: Parcel): SMS {
            return SMS(parcel)
        }

        override fun newArray(size: Int): Array<SMS?> {
            return arrayOfNulls(size)
        }
    }

}