package ru.s.pizza.models.food

import android.os.Parcel
import android.os.Parcelable

class Order() : Food(){
    var counter: Int = 0
    var code: Int = 0

    constructor(id: Int, name: String, picture: String, price: String,
                counter: String, code: Int) : this() {
        this.id = id
        this.name = name
        this.picture = picture.toInt()
        this.price = price.toFloat()
        this.counter = counter.toInt()
        this.code = code
    }

    constructor(id: Int, name: String, picture: Int, price: Float,
                counter: Int, code: Int) : this() {
        this.id = id
        this.name = name
        this.picture = picture
        this.price = price
        this.counter = counter
        this.code = code
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            val id: Int = parcel.readInt()
            val name = parcel.readString().toString()
            val picture: Int = parcel.readInt()
            val price: Float = parcel.readFloat()
            val counter: Int = parcel.readInt()
            val code: Int = parcel.readInt()
            return Order(id, name, picture, price, counter, code
            )
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeInt(id)
        out.writeString(name)
        out.writeInt(picture)
        out.writeFloat(price)
        out.writeInt(counter)
        out.writeInt(code)
    }

    override fun describeContents(): Int {
        return 0;
    }
}