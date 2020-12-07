package ru.s.pizza.models.food

import android.os.Parcel
import android.os.Parcelable

abstract class Food : Parcelable {
    var id: Int = 0
    var picture: Int = 0
    var name: String = ""
    var price = 0F
        set(value) {
            if (value > 0)
                field = value
        }
    var prices: List<Float> = listOf()

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeString(name)
    }

    fun strToListFloat(str: String): List<Float> {
        val strArr = str.split(" ".toRegex()).toTypedArray()
        val num = List(strArr.count()) { 0F }.toMutableList()
        for (i in strArr.indices) {
            try {
                num[i] = strArr[i].toFloat()
            } catch (exception: NumberFormatException) {
                return num
            }
        }
        return num
    }

    fun listFloatToString(list: List<Float>): String {
        var str = ""
        for (i in list) {
            str += i.toString()
            str += " "
        }
        return str
    }

    lateinit var strategy: IConfigurationStrategy

    fun getPrice (str: String): String {
        return strategy.getPrice(str)
    }
}

