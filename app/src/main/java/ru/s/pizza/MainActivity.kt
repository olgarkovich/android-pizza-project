package ru.s.pizza

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.s.pizza.serverWork.RetrofitImpl


class MainActivity : AppCompatActivity() {

    private val retrofitImpl: RetrofitImpl = RetrofitImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(R.id.navigation_food)

        navView.setOnNavigationItemSelectedListener { item ->
            when (item.title) {
                resources.getString(R.string.food) -> {
                    navController.navigate(R.id.navigation_food)
                }
                resources.getString(R.string.profile) -> {
                    navController.navigate(R.id.navigation_profile)
                }
                resources.getString(R.string.contacts) -> {
                    navController.navigate(R.id.navigation_contacts)
                }
                resources.getString(R.string.basket) -> {
                    navController.navigate(R.id.navigation_basket)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
}

