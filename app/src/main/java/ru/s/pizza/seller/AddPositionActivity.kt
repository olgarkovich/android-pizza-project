package ru.s.pizza.seller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import ru.s.pizza.R
import ru.s.pizza.foodFragments.food.AddDessertFragment
import ru.s.pizza.foodFragments.food.AddDrinkFragment
import ru.s.pizza.foodFragments.food.AddPizzaFragment

class AddPositionActivity : AppCompatActivity() {

    val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_position)

        showAddPizza()

        val addPizza = findViewById<Button>(R.id.add_pizza)
        val addDessert = findViewById<Button>(R.id.add_dessert)
        val addDrink = findViewById<Button>(R.id.add_drink)

        addPizza.setOnClickListener { showAddPizza() }
        addDessert.setOnClickListener { showAddDessert() }
        addDrink.setOnClickListener { showAddDrink() }
    }

    private fun showAddPizza() {
        val transaction = manager.beginTransaction()
        val fragment = AddPizzaFragment()
        transaction.replace(R.id.add_fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showAddDessert() {
        val transaction = manager.beginTransaction()
        val fragment = AddDessertFragment()
        transaction.replace(R.id.add_fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showAddDrink() {
        val transaction = manager.beginTransaction()
        val fragment = AddDrinkFragment()
        transaction.replace(R.id.add_fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        finish()
    }
}
