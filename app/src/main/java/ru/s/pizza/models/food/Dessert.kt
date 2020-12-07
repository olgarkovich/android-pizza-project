package ru.s.pizza.models.food

import android.os.Parcel
import android.os.Parcelable

class Dessert() : Food() {

    var weight = 0F
        set(value) {
            if (value > 0)
                field = value
        }

    var count: Int = 0
    var description: String = ""

    constructor(id: Int, name: String, price: String, weight: String,
                count: String, description: String, picture: Int) : this() {
        this.id = id
        this.name = name
        this.count = count.toInt()
        this.description = description
        this.price = price.toFloat()
        this.weight = weight.toFloat()
        this.picture = picture
    }

    constructor(id: Int, name: String, price: Float, weight: Float,
                count: Int, description: String, picture: Int) : this() {
        this.id = id
        this.name = name
        this.count = count
        this.description = description
        this.price = price
        this.weight = weight
        this.picture = picture
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeString(name)
        out.writeFloat(price)
        out.writeFloat(weight)
        out.writeInt(id)
        out.writeInt(count)
        out.writeString(description)
        out.writeInt(picture)
    }

    companion object CREATOR : Parcelable.Creator<Dessert> {
        override fun createFromParcel(parcel: Parcel): Dessert {
            val name: String = parcel.readString().toString()
            val price: Float = parcel.readFloat()
            val weight: Float = parcel.readFloat()
            val id: Int = parcel.readInt()
            val count: Int = parcel.readInt()
            val description: String = parcel.readString().toString()
            val picture: Int = parcel.readInt()

            return Dessert(id, name, price, weight, count, description, picture
            )
        }

        override fun newArray(size: Int): Array<Dessert?> {
            return arrayOfNulls(size)
        }
    }

    fun dessertPrice(): String {
        return "Добавить в корзину за ${this.price} руб."
    }

    fun showCount(): String {
        return "${this.count} шт."
    }
}