package ru.s.pizza.foodFragments.food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.s.pizza.R
import ru.s.pizza.models.FeedReaderDbHelper

class AddDrinkFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_drink, container, false)

        val name = view.findViewById<EditText>(R.id.add_name_drink)
        val prices = view.findViewById<EditText>(R.id.add_price_drink)
        val volumes = view.findViewById<EditText>(R.id.add_volume_drink)
        val save = view.findViewById<Button>(R.id.save_drink_button)
        val message = view.findViewById<TextView>(R.id.message_drink)

        save.setOnClickListener {
            if (name.text.toString() == "" || prices.text.toString() == "" ||
                volumes.text.toString() == "" )
                message.visibility = View.VISIBLE
            else {
                message.visibility = View.INVISIBLE
                val db = context?.let { it1 -> FeedReaderDbHelper(it1) }
                db?.writeDrink(name.text.toString(), prices.text.toString(),
                    volumes.text.toString())
                db?.close()
                Toast.makeText(context, "Add", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }
}