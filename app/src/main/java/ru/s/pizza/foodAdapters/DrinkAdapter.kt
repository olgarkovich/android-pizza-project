package ru.s.pizza.foodAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.s.pizza.R
import ru.s.pizza.models.food.Drink

class DrinkAdapter (context: Context, private val list: ArrayList<Drink>
) : RecyclerView.Adapter<DrinkAdapter.ViewHolder> () {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_list_drink, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    override fun getItemCount(): Int = list.size

    private fun getItem(position: Int) : Drink = list[position]

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.picture_drink_list)
        private val name: TextView = itemView.findViewById(R.id.name_drink_list)
        private val price: TextView = itemView.findViewById(R.id.price_drink_list)

        fun bind(drink: Drink) {
            image.setImageResource(drink.picture)
            name.text = drink.name
            price.text = "  от ${drink.price.toString()} руб.  "
        }
    }
}