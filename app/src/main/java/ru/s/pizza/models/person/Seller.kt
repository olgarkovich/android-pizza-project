package ru.s.pizza.models.person

import android.os.Parcel
import android.os.Parcelable

class Seller() : Person() {
    constructor(name: String, phone: String, mail: String) : this() {
        this.name = name
        this.phone = phone
        this.mail = mail
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeString(name)
        out.writeString(phone)
        out.writeString(mail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Seller> {
        override fun createFromParcel(parcel: Parcel): Seller {
            val name = parcel.readString().toString()
            val phone = parcel.readString().toString()
            val mail = parcel.readString().toString()

            return Seller(name, phone, mail)
        }

        override fun newArray(size: Int): Array<Seller?> {
            return arrayOfNulls(size)
        }
    }

}