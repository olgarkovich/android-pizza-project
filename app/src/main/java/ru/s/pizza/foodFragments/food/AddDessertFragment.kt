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

class AddDessertFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_dessert, container, false)

        val name = view.findViewById<EditText>(R.id.add_name_dessert)
        val price = view.findViewById<EditText>(R.id.add_price_dessert)
        val weight = view.findViewById<EditText>(R.id.add_weight_dessert)
        val count = view.findViewById<EditText>(R.id.add_count)
        val ingredient = view.findViewById<EditText>(R.id.add_ingredient_dessert)
        val save = view.findViewById<Button>(R.id.save_dessert_button)
        val message = view.findViewById<TextView>(R.id.message_dessert)

        save.setOnClickListener {
            if (name.text.toString() == "" || price.text.toString() == "" ||
                weight.text.toString() == "" || count.text.toString() == "" ||
                    ingredient.text.toString() == "")
                message.visibility = View.VISIBLE
            else {
                message.visibility = View.INVISIBLE
                val db = context?.let { it1 -> FeedReaderDbHelper(it1) }
                db?.writeDessert(name.text.toString(), price.text.toString(),
                    weight.text.toString(), count.text.toString(), ingredient.text.toString())
                db?.close()
                Toast.makeText(context, "Add", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }
}