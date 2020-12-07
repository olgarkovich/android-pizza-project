package ru.s.pizza.serverWork

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImpl {

    fun getRetrofit(): ServerAPI {
        val podRetrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.115:8080/app/")
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .build()
        return podRetrofit.create(ServerAPI::class.java)
    }
}