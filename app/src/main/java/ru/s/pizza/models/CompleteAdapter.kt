package ru.s.pizza.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import ru.s.pizza.R
import ru.s.pizza.models.food.Order

class CompleteAdapter(var context: Context, var order: ArrayList<Order>) : BaseAdapter() {

    private class ViewHolder(row: View?) {
        var textName: TextView = row?.findViewById(R.id.name_complete) as TextView
        var count: TextView = row?.findViewById(R.id.count_complete) as TextView
        var picture: ImageView = row?.findViewById(R.id.picture_complete) as ImageView

    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.item_list_complete, convertView, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }
        else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val order: Order = getItem(position) as Order
        viewHolder.textName.text = order.name
        viewHolder.count.text = order.counter.toString()
        viewHolder.picture.setImageResource(order.picture)

        return view as View
    }

    override fun getItem(p0: Int): Any {
        return order[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return order.count()
    }
}