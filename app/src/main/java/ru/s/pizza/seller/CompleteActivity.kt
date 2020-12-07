package ru.s.pizza.seller

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import ru.s.pizza.R
import ru.s.pizza.models.CompleteAdapter
import ru.s.pizza.models.FeedReaderDbHelper
import ru.s.pizza.models.food.Order


class CompleteActivity : AppCompatActivity() {
    lateinit var orders: ArrayList<Order>
    lateinit var barChart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete)

        val listView = findViewById<ListView>(R.id.complete_list)
        val diagram = findViewById<BarChart>(R.id.gr)
        val graph = findViewById<Button>(R.id.show_gr)
        val delete = findViewById<Button>(R.id.delete_history)

        val db = FeedReaderDbHelper(applicationContext)
        orders = db.readCompleteOrders()

        listView.adapter = CompleteAdapter(applicationContext, orders)


        graph.setOnClickListener {
            if (listView.isVisible) {
                listView.visibility = View.INVISIBLE
                diagram.visibility = View.VISIBLE
                barChart = findViewById(R.id.gr)
                val barDataSet1 = BarDataSet(dataValue1(), "Популярность продукции")
                barDataSet1.color = Color.RED
                val barData = BarData()
                barData.addDataSet(barDataSet1)
                barChart.data = barData
                val labels = ArrayList<String>()
                for (order in orders) {
                    labels.add(order.name)
                }
                barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                barChart.invalidate()
                barChart.animateY(500)
                graph.text = "Показать список"
            }
            else {
                listView.visibility = View.VISIBLE
                diagram.visibility = View.INVISIBLE
                graph.text = "Показать график"
            }
        }

        delete.setOnClickListener {
            val data = FeedReaderDbHelper(applicationContext)
            data.deleteCompleteOrders(orders)
            orders = arrayListOf()
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }

    }

    private fun dataValue1(): ArrayList<BarEntry> {
        val entries: ArrayList<BarEntry> = arrayListOf()
        for (order in orders) {
            entries.add(BarEntry(order.id.toFloat(), order.counter.toFloat()))
        }
        return entries
    }

    private fun getLabels(): ArrayList<String> {
        val entries = ArrayList<String>()
        for (order in orders) {
            entries.add(order.name)
        }
        return entries
    }

}
