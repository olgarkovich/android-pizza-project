package ru.s.pizza.models.food

import android.os.Parcel
import android.os.Parcelable

class Drink() : Food(){

    private var volumes: List<Float> = listOf()
    private var volume: Float = 0F

    init {
        strategy = DrinkConfigurationStrategy(this)
    }

    constructor (id: Int, name: String, prices: String, volumes: String, picture: Int) : this() {
        this.id = id
        this.name = name
        this.picture = picture
        this.prices = strToListFloat(prices)
        this.price = this.prices[0]
        this.volumes = strToListFloat(volumes)
        this.volume = this.volumes[0]
    }
    constructor(id: Int, name: String, picture: Int, price: Float,
                prices: String, volume: Float, volumes: String) : this() {
        this.id = id
        this.name = name
        this.picture = picture
        this.price = price
        this.prices = strToListFloat(prices)
        this.volume = volume
        this.volumes = strToListFloat(volumes)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeInt(id)
        out.writeString(name)
        out.writeInt(picture)
        out.writeFloat(price)
        out.writeString(listFloatToString(prices))
        out.writeFloat(volume)
        out.writeString((listFloatToString(volumes)))
    }

    companion object CREATOR : Parcelable.Creator<Drink> {
        override fun createFromParcel(parcel: Parcel): Drink {
            val id = parcel.readInt()
            val name = parcel.readString().toString()
            val picture = parcel.readInt()
            val price = parcel.readFloat()
            val prices = parcel.readString().toString()
            val volume = parcel.readFloat()
            val volumes = parcel.readString().toString()
            return Drink(id, name, picture, price, prices, volume, volumes
            )
        }

        override fun newArray(size: Int): Array<Drink?> {
            return arrayOfNulls(size)
        }
    }

   fun changeDrink(str: String): Drink {
        var i = 0
        when (str) {
            "0.5" -> i = 0
            "1" -> i = 1
            "1.5" -> i = 2
            "2" -> i = 3
        }
        this.price = this.prices[i]
        this.volume = this.volumes[i]

        return this
    }
}