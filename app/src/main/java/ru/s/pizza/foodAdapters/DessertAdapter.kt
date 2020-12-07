package ru.s.pizza.foodAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.s.pizza.R
import ru.s.pizza.models.food.Dessert

class DessertAdapter (context: Context, private val list: ArrayList<Dessert>
) : RecyclerView.Adapter<DessertAdapter.ViewHolder> () {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_list_dessert, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    override fun getItemCount(): Int = list.size

    private fun getItem(position: Int) : Dessert = list[position]

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.picture_dessert_list)
        private val name: TextView = itemView.findViewById(R.id.name_dessert_list)
        private val price: TextView = itemView.findViewById(R.id.price_dessert_list)
        private val ingredient: TextView = itemView.findViewById(R.id.description_dessert_list)

        fun bind(dessert: Dessert) {
            image.setImageResource(dessert.picture)
            name.text = dessert.name
            price.text = "  от ${dessert.price.toString()} руб.  "
            ingredient.text = dessert.description
        }
    }
}