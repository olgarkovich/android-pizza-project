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
import ru.s.pizza.foodAdapters.DessertAdapter
import ru.s.pizza.foodAdapters.RecyclerTouchListener
import ru.s.pizza.models.food.Dessert
import ru.s.pizza.models.FeedReaderDbHelper
import ru.s.pizza.serverWork.Server

class DessertFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_dessert, container, false)
        val context = requireContext().applicationContext
        val list = view.findViewById<RecyclerView>(R.id.dessertList)
        var listDessert: ArrayList<Dessert> = ArrayList()

        val sr = Server(context)
        if (sr.isOnline) {
            if (listDessert.size == 0) {
                Toast.makeText(context, "Connection", Toast.LENGTH_SHORT).show()
                var i = 1
                while (i < count + 1) {
                    sr.sendServerRequestDessert(context, list, i, listDessert)
                    i++
                }
            } else {
                renderData(context, list, listDessert)
            }

        } else {
            val db = FeedReaderDbHelper(context)
            listDessert = db.readDessert()
            renderData(context, list, listDessert)
        }

        list.addOnItemTouchListener(
            RecyclerTouchListener(context, list,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View?, position: Int) {
                        val intent = Intent(context, DessertPageActivity::class.java)
                        intent.putExtra("Dessert", listDessert[position])
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

    private fun renderData(context: Context, list: RecyclerView, listDessert: ArrayList<Dessert>) {

        val adapter = DessertAdapter(context, listDessert)
        list.adapter = adapter

        val decoration = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        decoration.setDrawable(ContextCompat.getDrawable(context, R.color.colorPrimary)!!)
        list.addItemDecoration(decoration)
    }

    val count = 5
}
