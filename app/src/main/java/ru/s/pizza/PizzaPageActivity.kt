package ru.s.pizza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_pizza_page.*
import ru.s.pizza.models.FeedReaderDbHelper
import ru.s.pizza.models.food.Pizza

class PizzaPageActivity : AppCompatActivity() {
    var pizza: Pizza =
        Pizza()
    var counterEgg = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_page)
        pizza = intent.getParcelableExtra<Pizza>("Pizza")!!

        val textName = findViewById<TextView>(R.id.name)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val ingredient = findViewById<TextView>(R.id.ingredient)
        val description = findViewById<TextView>(R.id.script)
        val buttonOrder = findViewById<Button>(R.id.to_order)

        buttonOrder.text = pizza.getPrice("little")
        description.text = pizza.description("little")
        textName.text = pizza.name
        imageView.setImageResource(pizza.picture)
        ingredient.text = pizza.ingredient

        to_order.setOnClickListener {
            val db = FeedReaderDbHelper(applicationContext)
            val code = db.readBuyerCode()
            db.insertData(pizza, code)
            db.close()
        }
    }

    fun onButtonLittleClick(view: View) {
        button_little.setBackgroundColor(getColor(R.color.colorPrimaryLight))
        button_medium.setBackgroundColor(getColor(R.color.colorButton))
        button_big.setBackgroundColor(getColor(R.color.colorButton))
        val description = findViewById<TextView>(R.id.script)
        description.text = pizza.description("little")
        to_order.text = pizza.getPrice("little")
        pizza.changePizza("little")

        showEgg(1)

    }

    fun onButtonMediumClick(view: View) {
        button_little.setBackgroundColor(getColor(R.color.colorButton))
        button_medium.setBackgroundColor(getColor(R.color.colorPrimaryLight))
        button_big.setBackgroundColor(getColor(R.color.colorButton))
        val description = findViewById<TextView>(R.id.script)
        description.text = pizza.description("medium")
        to_order.text = pizza.getPrice("medium")
        pizza.changePizza("medium")

        showEgg(3)
    }

    fun onButtonBigClick(view: View) {
        button_little.setBackgroundColor(getColor(R.color.colorButton))
        button_medium.setBackgroundColor(getColor(R.color.colorButton))
        button_big.setBackgroundColor(getColor(R.color.colorPrimaryLight))
        val description = findViewById<TextView>(R.id.script)
        description.text = pizza.description("big")
        to_order.text = pizza.getPrice("big")
        pizza.changePizza("big")

        showEgg(5)
    }

    private fun showEgg(point: Int) {
        counterEgg += point
        if (counterEgg == 25) {
            counterEgg = 0
            val intent = Intent(applicationContext, EggActivity::class.java)
            startActivity(intent)
        }
    }
}
