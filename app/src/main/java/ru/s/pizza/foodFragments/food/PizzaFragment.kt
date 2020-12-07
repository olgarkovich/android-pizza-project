package ru.s.pizza.foodFragments.food

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import ru.s.pizza.*
import ru.s.pizza.foodAdapters.PizzaAdapter
import ru.s.pizza.foodAdapters.RecyclerTouchListener
import ru.s.pizza.foodAdapters.RecyclerTouchListener.ClickListener
import ru.s.pizza.models.FeedReaderDbHelper
import ru.s.pizza.models.food.Pizza
import ru.s.pizza.serverWork.Server

class PizzaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_pizza, container, false)
        val context = requireActivity().applicationContext
        val list = view.findViewById<RecyclerView>(R.id.pizzaList)
        var listPizza: ArrayList<Pizza> = ArrayList()

        val sr = Server(context)
        if (sr.isOnline) {
            if (listPizza.size == 0) {
                Toast.makeText(context, "Connection", Toast.LENGTH_SHORT).show()
                var i = 1
                while (i < count + 1) {
                    sr.sendServerRequestPizza(context, list, i, listPizza)
                    i++
                }
            }
            else {
                renderData(context, list, listPizza)
            }

        } else {
            val db = FeedReaderDbHelper(context)
            listPizza = db.readPizza()
            renderData(context, list, listPizza)
        }

        list.addOnItemTouchListener(
            RecyclerTouchListener(context, list,
                object : ClickListener {
                    override fun onClick(view: View?, position: Int) {
                        val intent = Intent(context, PizzaPageActivity::class.java)
                        intent.putExtra("Pizza", listPizza[position])
                        startActivity(intent)
                    }
                    override fun onLongClick(view: View?, position: Int) {}
                })
        )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return super.onContextItemSelected(item)
    }

    private fun renderData(context: Context, list: RecyclerView, listPizza: ArrayList<Pizza>) {

        val adapter = PizzaAdapter(context, listPizza)
        list.adapter = adapter

        val decoration = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        decoration.setDrawable(ContextCompat.getDrawable(context, R.color.colorPrimary)!!)
        list.addItemDecoration(decoration)
    }

    val count = 10
}