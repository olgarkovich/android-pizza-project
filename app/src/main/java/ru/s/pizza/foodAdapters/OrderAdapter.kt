package ru.s.pizza.foodAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.s.pizza.R
import ru.s.pizza.models.FeedReaderDbHelper
import ru.s.pizza.models.food.Order

class OrderAdapter (val context: Context, private val list: ArrayList<Order>
) : RecyclerView.Adapter<OrderAdapter.ViewHolder> () {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_list_order, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    override fun getItemCount(): Int = list.size

    private fun getItem(position: Int) : Order = list[position]

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.order_picture)
        private val name: TextView = itemView.findViewById(R.id.order_name)
        private val pricer: TextView = itemView.findViewById(R.id.order_price)
        private val counter: TextView = itemView.findViewById(R.id.order_count)
        private val minusButton: Button = itemView.findViewById(R.id.button_minus)
        private val plusButton: Button = itemView.findViewById(R.id.button_plus)

        fun bind(order: Order) {
            image.setImageResource(order.picture)
            name.text = order.name
            pricer.text = "${((order.price * 10).toInt() * order.counter) / 10F} руб."
            counter.text = order.counter.toString()

            minusButton.setOnClickListener(View.OnClickListener {
                if (order.counter > 0) {
                    order.counter = order.counter - 1
                    counter.text = order.counter.toString()
                    val price: Int = (order.price * 10).toInt() * order.counter
                    pricer.text = "${price / 10F} руб."
                }

                val db = FeedReaderDbHelper(context)
                if (order.counter > 0)
                    db.updateData(order)
                else {
                    db.deleteData(order)
                }
            })

            plusButton.setOnClickListener(View.OnClickListener {
                if (order.counter < 25) {
                    order.counter = order.counter + 1
                    counter.text = order.counter.toString()
                    val price: Int = (order.price * 10).toInt() * order.counter
                    pricer.text = "${price / 10F} руб."
                }

                val db = FeedReaderDbHelper(context)
                if(order.counter == 1)
                    db.insertData(order, 0)
                else
                    db.updateData(order)
            })

        }


    }
}