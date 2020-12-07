package ru.s.pizza.seller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import ru.s.pizza.R
import ru.s.pizza.models.FeedReaderDbHelper
import ru.s.pizza.models.person.Seller
import ru.s.pizza.foodFragments.CustomDialogFragment

class SellerActivity : AppCompatActivity() {
    lateinit var seller: Seller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller)

        val changeProfile = findViewById<Button>(R.id.change_seller_button)
        val anotherProfile = findViewById<Button>(R.id.another_profile_seller)
        val addPosition = findViewById<Button>(R.id.add_position)
        val showComplete = findViewById<Button>(R.id.watch_statistic)

        val name = findViewById<EditText>(R.id.seller_name)
        val phone = findViewById<EditText>(R.id.seller_phone)
        val mail = findViewById<EditText>(R.id.seller_mail)

        val db = FeedReaderDbHelper(applicationContext)
        seller = db.readSeller()

        name.setText(seller.name)
        phone.setText(seller.phone)
        mail.setText(seller.mail)

        changeProfile?.setOnClickListener {
            if (name.isEnabled) {
                changeProfile.text = "Редактировать"
                name.isEnabled = false
                phone.isEnabled = false
                mail.isEnabled = false
                anotherProfile.visibility = View.VISIBLE
                addPosition.visibility = View.VISIBLE

                seller.name = name.text.toString()
                seller.phone = phone.text.toString()
                seller.mail = mail.text.toString()
                db.updateSeller(seller)
            }
            else {
                changeProfile.text = "Сохранить"
                name.isEnabled = true
                phone.isEnabled = true
                mail.isEnabled = true
                anotherProfile.visibility = View.INVISIBLE
                addPosition.visibility = View.INVISIBLE
            }
        }

        anotherProfile?.setOnClickListener {
            val dialogFragment = CustomDialogFragment("Владелец")
            fragmentManager?.let { dialogFragment.show(supportFragmentManager, "ssss") }
        }

        addPosition.setOnClickListener {
            val intent = Intent(applicationContext, AddPositionActivity::class.java)
            startActivity(intent)
        }

        showComplete.setOnClickListener {
            val intent = Intent(applicationContext, CompleteActivity::class.java)
            startActivity(intent)
        }
    }
}
