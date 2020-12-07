package ru.s.pizza.foodAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.s.pizza.R
import ru.s.pizza.models.food.Pizza

class PizzaAdapter (context: Context, private val list: ArrayList<Pizza>
) : RecyclerView.Adapter<PizzaAdapter.ViewHolder> () {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_list_pizza, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    override fun getItemCount(): Int = list.size

    private fun getItem(position: Int) : Pizza = list[position]

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.picture_pizza_list)
        private val name: TextView = itemView.findViewById(R.id.name_pizza_list)
        private val price: TextView = itemView.findViewById(R.id.price_pizza_list)
        private val ingredient: TextView = itemView.findViewById(R.id.description_pizza_list)

        fun bind(pizza: Pizza) {
            image.setImageResource(pizza.picture)
            name.text = pizza.name
            price.text = "  от ${pizza.price.toString()} руб.  "
            ingredient.text = pizza.ingredient
        }
    }
}