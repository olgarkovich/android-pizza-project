package ru.s.pizza.seller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import ru.s.pizza.R
import ru.s.pizza.foodAdapters.LogAdapter
import ru.s.pizza.models.FeedReaderDbHelper
import ru.s.pizza.models.PizzaLog

class LogActivity : AppCompatActivity() {

    private val logs = arrayListOf<PizzaLog>()

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        recyclerView = findViewById(R.id.logRecycler)

        val db = FeedReaderDbHelper(applicationContext)
        logs.addAll(db.readPizzaLogs())

        val adapter = LogAdapter()
        adapter.setPizzaLog(logs)
        recyclerView.adapter = adapter
    }
}