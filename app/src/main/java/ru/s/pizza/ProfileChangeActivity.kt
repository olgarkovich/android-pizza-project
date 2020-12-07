package ru.s.pizza

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import ru.s.pizza.models.FeedReaderDbHelper
import ru.s.pizza.models.person.Buyer

class ProfileChangeActivity : AppCompatActivity() {
    var buyer = Buyer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_change)

        buyer = intent.getParcelableExtra<Buyer>("Buyer")!!

        val name = findViewById<EditText>(R.id.name_profile)
        val phone = findViewById<EditText>(R.id.phone_profile)
        val mail = findViewById<EditText>(R.id.mail_profile)
        val birth = findViewById<EditText>(R.id.birth_profile)
        val address = findViewById<EditText>(R.id.address_profile)
        val message = findViewById<TextView>(R.id.message_change_profile)
        val saveButton = findViewById<Button>(R.id.save_button)

        name.setText(buyer.name)
        phone.setText(buyer.phone)
        mail.setText(buyer.mail)
        birth.setText(buyer.birth)
        address.setText(buyer.address)

        saveButton.setOnClickListener {
            if (name.text.toString() == "" || phone.text.toString() == "" || mail.text.toString() == "" ||
                birth.text.toString() == "" || address.text.toString() == "") {
                message.visibility = View.VISIBLE
            }
            else {
                val buyer = Buyer(
                    name.text.toString(), phone.text.toString(), mail.text.toString(),
                    birth.text.toString(), address.text.toString(), buyer.code
                )
                val db = FeedReaderDbHelper(applicationContext)
                db.updateData(buyer)
                val intent = Intent()
                intent.putExtra("Buyer", buyer)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }


}
