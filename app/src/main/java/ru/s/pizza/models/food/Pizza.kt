package ru.s.pizza.models.food

import android.os.Parcel
import android.os.Parcelable


class Pizza() : Food() {

    private var diameterList: List<Byte> = listOf(32, 35, 40)

    var ingredient: String = ""
    var weight = 0F
        set(value) {
            if (value > 0)
                field = value
        }
    private var weights: List<Float> = listOf()

    init {
        strategy = PizzaConfigurationStrategy(this)
    }

    constructor(id: Int, name: String, prices: String,
                weights: String, ingredient: String, picture: Int) : this() {
        this.id = id
        this.name = name
        this.picture = picture
        this.prices = strToListFloat(prices)
        this.price = this.prices[0]
        this.weights = strToListFloat(weights)
        this.weight = this.weights[0]
        this.ingredient = ingredient
    }

    override fun describeContents(): Int {
        return 0;
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeInt(id)
        out.writeString(name)
        out.writeInt(picture)
        out.writeFloat(price)
        out.writeString(listFloatToString(prices))
        out.writeString(listFloatToString(weights))
        out.writeString(ingredient)
    }

    constructor(id: Int, name: String, picture: Int, price: Float,
                prices: String, weights: String, ingredient: String) : this() {
        this.id = id
        this.name = name
        this.picture = picture
        this.price = price
        this.prices = strToListFloat(prices)
        this.weights = strToListFloat(weights)
        this.ingredient = ingredient
    }

    companion object CREATOR : Parcelable.Creator<Pizza> {
        override fun createFromParcel(parcel: Parcel): Pizza {
            val id: Int = parcel.readInt()
            val name = parcel.readString().toString()
            val picture: Int = parcel.readInt()
            val price: Float = parcel.readFloat()
            val prices: String = parcel.readString().toString()
            val weights: String = parcel.readString().toString()
            val ingredient: String = parcel.readString().toString()
            return Pizza(id, name, picture, price, prices, weights, ingredient
            )
        }

        override fun newArray(size: Int): Array<Pizza?> {
            return arrayOfNulls(size)
        }
    }

    fun description(str: String): String {
        var i = 0
        when (str) {
            "little" -> i = 0
            "medium" -> i = 1
            "big" -> i = 2
        }
        return "${this.diameterList[i]} см, ${this.weights[i]} кг"
    }

    fun changePizza(str: String): Pizza {
        var i = 0
        when (str) {
            "little" -> i = 0
            "medium" -> i = 1
            "big" -> i = 2
        }
        this.price = this.prices[i]
        this.weight = this.weights[i]

        return this
    }
}