package ru.s.pizza.signing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import ru.s.pizza.MainActivity
import ru.s.pizza.PrefManager
import ru.s.pizza.R
import ru.s.pizza.models.FeedReaderDbHelper
import ru.s.pizza.models.PizzaLog
import ru.s.pizza.models.person.Buyer
import ru.s.pizza.models.person.Seller
import ru.s.pizza.seller.SellerActivity
import java.text.SimpleDateFormat
import java.util.*

class SignActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var regButton: Button
    private lateinit var checkBox: CheckBox
    private lateinit var loginEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private var buyer = Buyer()
    private var seller = Seller()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        button = findViewById(R.id.signInButton)
        regButton = findViewById(R.id.registerButton)
        checkBox = findViewById(R.id.checkBox)
        loginEditText = findViewById(R.id.loginTextEdit)
        passwordEditText = findViewById(R.id.passwordTextEdit)

        button.setOnClickListener {
            checkSigning()
        }

        regButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    private fun checkSigning() {
        val db = FeedReaderDbHelper(this)

        if (!checkBox.isChecked) {
            buyer = db.readBuyer(loginEditText.text.toString())

            if (loginEditText.text.toString() != "" && buyer.password == passwordEditText.text.toString().hashCode().toString()) {

                val pref = PrefManager(this)
                pref.setLogin(buyer.login)
                pref.setPasswordHash(buyer.password)

                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault())
                db.writePizzaLog(PizzaLog(sdf.format(Date()), pref.getLogin(), "Пользователь авторизовался"))

                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                Toast.makeText(this, "Проверьте логин или пароль", Toast.LENGTH_SHORT).show()
            }
        } else {
            seller = db.readSeller(loginEditText.text.toString())

            if (loginEditText.text.toString() != "" && seller.password == passwordEditText.text.toString().hashCode().toString()) {

                val pref = PrefManager(this)
                pref.setLogin(seller.login)
                pref.setPasswordHash(seller.password)

                val intent = Intent(this, SellerActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                Toast.makeText(this, "Проверьте логин или пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }
}