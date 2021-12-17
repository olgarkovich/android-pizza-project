package ru.s.pizza.models.person

import android.os.Parcel
import android.os.Parcelable

class Seller() : Person() {
    constructor(name: String, phone: String, mail: String) : this() {
        this.name = name
        this.phone = phone
        this.mail = mail
    }

    constructor(name: String, phone: String, mail: String, login: String, password: String) : this() {
        this.name = name
        this.phone = phone
        this.mail = mail
        this.login = login
        this.password = password
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeString(name)
        out.writeString(phone)
        out.writeString(mail)
    }

    constructor (login: String, password: String) : this() {
        this.login = login
        this.password = password
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Seller> {
        override fun createFromParcel(parcel: Parcel): Seller {
            val name = parcel.readString().toString()
            val phone = parcel.readString().toString()
            val mail = parcel.readString().toString()
            val login = parcel.readString().toString()
            val password = parcel.readString().toString()

            return Seller(name, phone, mail, login, password)
        }

        override fun newArray(size: Int): Array<Seller?> {
            return arrayOfNulls(size)
        }
    }

}