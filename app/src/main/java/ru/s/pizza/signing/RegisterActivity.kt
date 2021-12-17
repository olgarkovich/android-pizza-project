package ru.s.pizza.signing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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

class RegisterActivity : AppCompatActivity() {

    private lateinit var regButton: Button
    private lateinit var checkBox: CheckBox
    private lateinit var loginEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var repeatPasswordEditText: TextInputEditText
    private lateinit var keyEditText: TextInputEditText
    private lateinit var keyInputLayout: TextInputLayout
    private var buyer = Buyer()
    private var seller = Seller()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        regButton = findViewById(R.id.registerButton)
        checkBox = findViewById(R.id.checkBox)
        loginEditText = findViewById(R.id.loginTextEdit)
        passwordEditText = findViewById(R.id.passwordTextEdit)
        repeatPasswordEditText = findViewById(R.id.passwordRepitTextEdit)
        keyEditText = findViewById(R.id.keyTextEdit)
        keyInputLayout = findViewById(R.id.keyTextField)

        regButton.setOnClickListener {
            registerUser()
        }

        checkBox.setOnClickListener {
            if (checkBox.isChecked) {
                keyInputLayout.visibility = View.VISIBLE
            } else {
                keyInputLayout.visibility = View.INVISIBLE
            }
        }
    }

    private fun registerUser() {
        val db = FeedReaderDbHelper(this)

        if (!checkBox.isChecked) {
            if (loginEditText.text.toString() != "" && passwordEditText.text.toString() != "" && passwordEditText.text.toString() == repeatPasswordEditText.text.toString()) {
                buyer.login = loginEditText.text.toString()
                buyer.password = passwordEditText.text.toString().hashCode().toString()
                db.writeBuyer(buyer)

                val pref = PrefManager(this)
                pref.setLogin(buyer.login)
                pref.setPasswordHash(buyer.password)

                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault())
                db.writePizzaLog(PizzaLog(sdf.format(Date()), pref.getLogin(), "Пользователь зарегистрировался"))

                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else {
                Toast.makeText(this, "Проверьте логин или пароль", Toast.LENGTH_SHORT).show()
            }
        } else {
            if (loginEditText.text.toString() != "" && passwordEditText.text.toString() != "" && passwordEditText.text.toString() == repeatPasswordEditText.text.toString() && keyEditText.text.toString() == "1235") {
                seller.login = loginEditText.text.toString()
                seller.password = passwordEditText.text.toString().hashCode().toString()
                db.writeSeller(seller)

                val pref = PrefManager(this)
                pref.setLogin(seller.login)
                pref.setPasswordHash(seller.password)

                val intent = Intent(this, SellerActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else {
                Toast.makeText(this, "Проверьте логин или пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }
}