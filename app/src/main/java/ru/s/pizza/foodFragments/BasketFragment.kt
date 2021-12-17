package ru.s.pizza.foodFragments

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import ru.s.pizza.PrefManager
import ru.s.pizza.R
import ru.s.pizza.foodAdapters.OrderAdapter
import ru.s.pizza.models.*
import ru.s.pizza.models.food.Order
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BasketFragment : Fragment() {
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "com.example.vicky.notificationexample"
    private val description = "Pizza"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_basket, container, false)
        val context = requireActivity().applicationContext
        val list = view.findViewById<RecyclerView>(R.id.orderList)
        val listOrder: ArrayList<Order> = ArrayList()
        val orderButton = view.findViewById<Button>(R.id.order)
        var cost = 0F

        val myDatabase = context?.let { FeedReaderDbHelper(it).readableDatabase }
        val query = "SELECT id, name, picture, price, count FROM OrderList"
        val m: Array<out String> = arrayOf()
        val mCur = myDatabase?.rawQuery(query, m)

        if(mCur?.moveToFirst() == true) {
            var index = 0
            do {
                val name = mCur.getString(mCur.getColumnIndex("name"))
                val picture = mCur.getString(mCur.getColumnIndex("picture"))
                val price = mCur.getString(mCur.getColumnIndex("price"))
                val count = mCur.getString(mCur.getColumnIndex("count"))

                val p = Order(index, name, picture, price, count, 0)
                index += 1
                listOrder.add(p)
                cost += calculateCost(price.toFloat(), count.toInt())
            } while (mCur.moveToNext())
        }
        mCur?.close()
        myDatabase?.close()

        orderButton.text = "Оформить за ${cost} руб."

        renderData(context, list, listOrder)

        notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(context, LauncherActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        orderButton.setOnClickListener {
            var checkCost = 0F
            for (order in listOrder) {

                checkCost += calculateCost(order.price, order.counter)
            }
            if (checkCost == getCost(orderButton)) {
                Toast.makeText(context,"Ok", Toast.LENGTH_SHORT).show()
                val db = FeedReaderDbHelper(context)
                db.writeOrderList(listOrder)
                db.close()
                notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(context, channelId)
                    .setContentTitle("Pam-Pam")
                    .setContentText("Заказ принят")
                    .setSmallIcon(R.mipmap.icons_launch)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.icons_launch))
                    .setContentIntent(pendingIntent)

                notificationManager.notify(1234, builder.build())

                val pref = PrefManager(requireContext())
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault())
                db.writePizzaLog(PizzaLog(sdf.format(Date()), pref.getLogin(), "Пользователь сделал заказ"))
            }
            else {
                orderButton.text = "Оформить заказ за ${checkCost} руб."
            }

        }
        return view
    }

    private fun getCost(btn: Button): Float {
        val strArr = btn.text.split(" ".toRegex()).toTypedArray()
        var num = 0F
        for (i in strArr.indices) {
            try {
                num = strArr[i].toFloat()
                break
            } catch (exception: NumberFormatException) {}
        }
        return num
    }

    private fun calculateCost(price: Float, count: Int): Float {
        val priceInt = (price * 10).toInt() * count
        return priceInt / 10F
    }

    private fun renderData(context: Context, list: RecyclerView, listOrder: ArrayList<Order>) {

        val adapter = OrderAdapter(context, listOrder)
        list.adapter = adapter

        val decoration = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        decoration.setDrawable(ContextCompat.getDrawable(context, R.color.colorPrimary)!!)
        list.addItemDecoration(decoration)
    }
}
