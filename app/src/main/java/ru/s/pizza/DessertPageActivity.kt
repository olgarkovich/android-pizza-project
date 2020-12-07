package ru.s.pizza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_dessert_page.*
import ru.s.pizza.models.food.Dessert
import ru.s.pizza.models.FeedReaderDbHelper

class DessertPageActivity : AppCompatActivity() {
    var dessert: Dessert =
        Dessert()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dessert_page)

        dessert = intent.getParcelableExtra<Dessert>("Dessert")!!

        val textName = findViewById<TextView>(R.id.name_dessert_list)
        val imageView = findViewById<ImageView>(R.id.image_dessert)
        val description = findViewById<TextView>(R.id.description_dessert_list)
        val buttonOrder = findViewById<Button>(R.id.dessert_to_order)
        val count = findViewById<TextView>(R.id.dessert_count)

        buttonOrder.text = dessert.dessertPrice()
        textName.text = dessert.name
        imageView.setImageResource(dessert.picture)
        description.text = dessert.description
        count.text = dessert.showCount()

        dessert_to_order.setOnClickListener {
            val db = FeedReaderDbHelper(applicationContext)
            val code = db.readBuyerCode()
            db.insertData(dessert, code)
            db.close()
        }
    }
}
