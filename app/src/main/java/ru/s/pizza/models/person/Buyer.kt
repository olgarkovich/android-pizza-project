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

    constructor (name: String, phone: String, mail: String,
                 birth: String, address: String, code: Int, login: String, password: String) : this() {
        this.name = name
        this.phone = phone
        this.mail = mail
        this.birth = birth
        this.address = address
        this.code = code
        this.login = login
        this.password = password
    }

    constructor (login: String, password: String) : this() {
        this.login = login
        this.password = password
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeString(name)
        out.writeString(phone)
        out.writeString(mail)
        out.writeString(birth)
        out.writeString(address)
        out.writeInt(code)
        out.writeString(login)
        out.writeString(password)
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
            val login = parcel.readString().toString()
            val password = parcel.readString().toString()
            return Buyer(
                name, phone, mail, birth, address, code, login, password
            )
        }

        override fun newArray(size: Int): Array<Buyer?> {
            return arrayOfNulls(size)
        }
    }
}