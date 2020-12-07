package ru.s.pizza.serverWork

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ServerAPI {

    @GET("pizza/{id}")
    fun getPizza(@Path("id") id: Int): Call<PizzaData>

    @GET("dessert/{id}")
    fun getDessert(@Path("id") id: Int): Call<DessertData>

    @GET("drink/{id}")
    fun getDrink(@Path("id") id: Int): Call<DrinkData>

    @GET("pizza/count")
    fun pizzaCount(): Call<Long>
}
