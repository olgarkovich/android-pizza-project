package ru.s.pizza.foodAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.s.pizza.R
import ru.s.pizza.models.PizzaLog

class LogAdapter : RecyclerView.Adapter<LogAdapter.LogHolder>() {

    var listener: ((Int) -> Unit)? = null
    private var logList = emptyList<PizzaLog>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_log, parent, false)

        return LogHolder(view)
    }

    fun setPizzaLog(pizzaLogs: List<PizzaLog>) {
        logList = pizzaLogs
    }

    override fun onBindViewHolder(holder: LogHolder, position: Int) {
        holder.time.text = logList[position].time
        holder.login.text = logList[position].login
        holder.action.text = logList[position].action
    }

    override fun getItemCount(): Int = logList.size

    inner class LogHolder(view: View): RecyclerView.ViewHolder(view){
        val time: TextView = view.findViewById(R.id.timeText)
        val login: TextView = view.findViewById(R.id.loginText)
        val action: TextView = view.findViewById(R.id.actionText)
    }
}