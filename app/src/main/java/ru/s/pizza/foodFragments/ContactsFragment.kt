package ru.s.pizza.foodFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import ru.s.pizza.MapActivity
import ru.s.pizza.PrefManager
import ru.s.pizza.R
import ru.s.pizza.models.FeedReaderDbHelper
import ru.s.pizza.models.PizzaLog
import java.text.SimpleDateFormat
import java.util.*

class ContactsFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contacts, container, false)
        val button = view.findViewById<Button>(R.id.button)

        val db = FeedReaderDbHelper(requireContext())
        button.setOnClickListener {
            val pref = PrefManager(requireContext())
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault())
            db.writePizzaLog(PizzaLog(sdf.format(Date()), pref.getLogin(), "Пользователь открыл карту"))

            val  intent = Intent(context, MapActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}
