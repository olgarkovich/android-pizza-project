package ru.s.pizza.models.person

import android.os.Parcel
import android.os.Parcelable

class Buyer() : Person() {
    var address: String = ""
    var birth: String = ""
    var code: Int = 0

    constructor (name: String, phone: String, mail: String,
                 birth: String, address: String, code: Int) : this() {
        this.name = name
        this.phone = phone
        this.mail = mail
        this.birth = birth
        this.address = address
        this.code = code
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeString(name)
        out.writeString(phone)
        out.writeString(mail)
        out.writeString(birth)
        out.writeString(address)
        out.writeInt(code)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Buyer> {
        override fun createFromParcel(parcel: Parcel): Buyer {
            val name: String = parcel.readString().toString()
            val phone: String = parcel.readString().toString()
            val mail: String = parcel.readString().toString()
            val birth: String = parcel.readString().toString()
            val address: String = parcel.readString().toString()
            val code: Int = parcel.readInt()
            return Buyer(
                name, phone, mail, birth, address, code
            )
        }

        override fun newArray(size: Int): Array<Buyer?> {
            return arrayOfNulls(size)
        }
    }
}