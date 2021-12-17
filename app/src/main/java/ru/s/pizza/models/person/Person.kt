package ru.s.pizza.models.person

import android.os.Parcel
import android.os.Parcelable

abstract class Person : Parcelable {
    var name: String = ""
    var phone: String = ""
    var mail: String = ""
    var login: String = ""
    var password: String = ""

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeString(name)
    }
}