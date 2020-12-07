package ru.s.pizza.serverWork

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.s.pizza.R
import ru.s.pizza.foodAdapters.DessertAdapter
import ru.s.pizza.foodAdapters.DrinkAdapter
import ru.s.pizza.foodAdapters.PizzaAdapter
import ru.s.pizza.models.food.Dessert
import ru.s.pizza.models.food.Drink
import ru.s.pizza.models.food.Pizza

class Server (val context: Context) {

    private val retrofitImpl: RetrofitImpl = RetrofitImpl()
    val isOnline = isOnline(context)

    private fun isOnline(context: Context?): Boolean {

        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val n = cm.activeNetwork
        if (n != null) {
            val nc = cm.getNetworkCapabilities(n)
            return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            )
        }
        return false
    }

    fun sendServerRequestDessert(context: Context?, list: RecyclerView, i: Int, listDessert: ArrayList<Dessert>) {

        retrofitImpl.getRetrofit().getDessert(i).enqueue(object : Callback<DessertData> {

            override fun onResponse(
                call: Call<DessertData>, response: Response<DessertData>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    //val str = response.body()?.name
                    //Toast.makeText(context, str + "!!!!", Toast.LENGTH_SHORT).show()

                    val dessert = Dessert(
                        response.body()!!.id,
                        response.body()!!.name,
                        response.body()!!.price,
                        response.body()!!.weight,
                        response.body()!!.count,
                        response.body()!!.ingredient,
                        context?.resources?.getIdentifier(
                            response.body()!!.picture,
                            "drawable", context.packageName
                        )!!
                    )
                    listDessert.add(dessert)
                }

                if (i == 5) {
                    val adapter = DessertAdapter(context!!, listDessert)

                    list.adapter = adapter

                    val decoration =
                        DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
                    decoration.setDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.color.colorPrimary
                        )!!
                    )
                    list.addItemDecoration(decoration)
                }
            }

            override fun onFailure(call: Call<DessertData>, t: Throwable) {
                //
            }
        })
    }

    fun sendServerRequestPizza(context: Context?, list: RecyclerView, i: Int, listPizza: ArrayList<Pizza>) {

        retrofitImpl.getRetrofit().getPizza(i).enqueue(object : Callback<PizzaData> {

            override fun onResponse(
                call: Call<PizzaData>, response: Response<PizzaData>
            ) {

                if (response.isSuccessful && response.body() != null) {
                    //val str = response.body()?.name
                    //Toast.makeText(context, str + "!!!!", Toast.LENGTH_SHORT).show()

                    val pizza = Pizza(
                        response.body()!!.id, response.body()!!.name, response.body()!!.prices,
                        response.body()!!.weights, response.body()!!.ingredient,
                        context?.resources?.getIdentifier(response.body()!!.picture,
                            "drawable", context.packageName)!!)
                    listPizza.add(pizza)
                }

                val adapter = PizzaAdapter(context!!, listPizza)

                list.adapter = adapter

                val decoration =
                    DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
                decoration.setDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.color.colorPrimary
                    )!!
                )
                list.addItemDecoration(decoration)

            }

            override fun onFailure(call: Call<PizzaData>, t: Throwable) {
                //
            }
        })
    }

    fun sendServerRequestDrink(context: Context?, list: RecyclerView, i: Int, listDrink: ArrayList<Drink>) {

        retrofitImpl.getRetrofit().getDrink(i).enqueue(object : Callback<DrinkData> {

            override fun onResponse(
                call: Call<DrinkData>, response: Response<DrinkData>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    //val str = response.body()?.name
                    //Toast.makeText(context, str + "!!!!", Toast.LENGTH_SHORT).show()

                    val drink = Drink(
                        response.body()!!.id, response.body()!!.name, response.body()!!.prices,
                        response.body()!!.volumes,
                        context?.resources?.getIdentifier(response.body()!!.picture,
                            "drawable", context.packageName)!!)
                    listDrink.add(drink)
                }

                val adapter = DrinkAdapter(context!!, listDrink)

                list.adapter = adapter

                val decoration =
                    DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
                decoration.setDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.color.colorPrimary
                    )!!
                )
                list.addItemDecoration(decoration)

            }

            override fun onFailure(call: Call<DrinkData>, t: Throwable) {
                //
            }
        })
    }
}