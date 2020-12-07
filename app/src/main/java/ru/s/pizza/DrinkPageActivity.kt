package ru.s.pizza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_drink_page.*
import ru.s.pizza.models.food.Drink
import ru.s.pizza.models.FeedReaderDbHelper

class DrinkPageActivity : AppCompatActivity() {
    var drink: Drink =
        Drink()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_page)

        drink = intent.getParcelableExtra<Drink>("Drink")!!

        val textName = findViewById<TextView>(R.id.name_drink)
        val imageView = findViewById<ImageView>(R.id.image_drink)
        val buttonOrder = findViewById<Button>(R.id.drink_to_order)
        buttonOrder.text = drink.getPrice("0.5")
        textName.text = drink.name
        imageView.setImageResource(drink.picture)

        drink_to_order.setOnClickListener {
            val db = FeedReaderDbHelper(applicationContext)
            val code = db.readBuyerCode()
            db.insertData(drink, code)
            db.close()
        }
    }

    fun button05(view: View) {
        _0_5.setBackgroundColor(getColor(R.color.colorPrimaryLight))
        _1.setBackgroundColor(getColor(R.color.colorButton))
        _1_5.setBackgroundColor(getColor(R.color.colorButton))
        _2.setBackgroundColor(getColor(R.color.colorButton))
        drink_to_order.text = drink.getPrice("0.5")
        drink.changeDrink("0.5")
    }

    fun button1(view: View) {
        _0_5.setBackgroundColor(getColor(R.color.colorButton))
        _1.setBackgroundColor(getColor(R.color.colorPrimaryLight))
        _1_5.setBackgroundColor(getColor(R.color.colorButton))
        _2.setBackgroundColor(getColor(R.color.colorButton))
        drink_to_order.text = drink.getPrice("1")
        drink.changeDrink("1")
    }

    fun button15(view: View) {
        _0_5.setBackgroundColor(getColor(R.color.colorButton))
        _1.setBackgroundColor(getColor(R.color.colorButton))
        _1_5.setBackgroundColor(getColor(R.color.colorPrimaryLight))
        _2.setBackgroundColor(getColor(R.color.colorButton))
        drink_to_order.text = drink.getPrice("1.5")
        drink.changeDrink("1.5")
    }

    fun button2(view: View) {
        _0_5.setBackgroundColor(getColor(R.color.colorButton))
        _1.setBackgroundColor(getColor(R.color.colorButton))
        _1_5.setBackgroundColor(getColor(R.color.colorButton))
        _2.setBackgroundColor(getColor(R.color.colorPrimaryLight))
        drink_to_order.text = drink.getPrice("2")
        drink.changeDrink("2")
    }
}
