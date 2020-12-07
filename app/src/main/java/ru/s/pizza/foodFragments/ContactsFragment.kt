package ru.s.pizza.foodFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import ru.s.pizza.MapActivity
import ru.s.pizza.R

class ContactsFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contacts, container, false)
        val button = view.findViewById<Button>(R.id.button)

        button.setOnClickListener {
            val  intent = Intent(context, MapActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}
