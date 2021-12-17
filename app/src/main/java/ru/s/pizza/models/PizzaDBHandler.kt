package ru.s.pizza.models

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.s.pizza.PrefManager
import ru.s.pizza.R
import ru.s.pizza.models.food.*
import ru.s.pizza.models.person.Buyer
import ru.s.pizza.models.person.Seller
import java.io.File
import java.io.FileOutputStream

class FeedReaderDbHelper(var context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) { }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) { }
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "pizzaDB"
        const val ASSETS_PATH = "database"
    }

    private fun installDatabaseFromAssets() {
        val inputStream = context.assets.open("$ASSETS_PATH/$DATABASE_NAME.db")

        try {
            val outputFile = File(context.getDatabasePath(DATABASE_NAME).path)
            val outputStream = FileOutputStream(outputFile)

            inputStream.copyTo(outputStream)
            inputStream.close()

            outputStream.flush()
            outputStream.close()
        } catch (exception: Throwable) {
            throw RuntimeException("The $DATABASE_NAME database couldn't be installed.", exception)
        }
    }

    private val preferences: SharedPreferences = context.getSharedPreferences(
        "${context.packageName}.database_versions",
        Context.MODE_PRIVATE
    )

    private fun installedDatabaseIsOutdated(): Boolean {
        return preferences.getInt(DATABASE_NAME, 0) < DATABASE_VERSION
    }

    private fun writeDatabaseVersionInPreferences() {
        preferences.edit().apply {
            putInt(DATABASE_NAME, DATABASE_VERSION)
            apply()
        }
    }

    @Synchronized
    private fun installOrUpdateIfNecessary() {
        if (installedDatabaseIsOutdated()) {
            context.deleteDatabase(DATABASE_NAME)
            installDatabaseFromAssets()
            writeDatabaseVersionInPreferences()
        }
    }

    override fun getReadableDatabase(): SQLiteDatabase {
        installOrUpdateIfNecessary()
        return super.getReadableDatabase()
    }

    fun insertData (order: Food, code: Int) {

        if (checkFoodCode(order, code)) {

            val db = this.writableDatabase
            val cv = ContentValues()

            cv.put("name", order.name)
            cv.put("picture", order.picture)
            cv.put("price", order.price.toString())
            cv.put("count", 1.toString())
            cv.put("code", code.toString())

            db.insert("OrderList", null, cv)
        }
    }

    fun updateData(order: Order) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("count", order.counter.toString())

        db.update("OrderList", cv, "name = ? AND price = ?",
            arrayOf(order.name, order.price.toString()))
    }

    fun deleteData(order: Order) {
        val db = this.writableDatabase
        db.delete("OrderList", "name = ? AND price = ?",
            arrayOf(order.name, order.price.toString()))
    }

    fun updateData(buyer: Buyer) {
        val pref = PrefManager(context)

        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("name", buyer.name)
        cv.put("phone", buyer.phone)
        cv.put("mail", buyer.mail)
        cv.put("birth", buyer.birth)
        cv.put("address", buyer.address)
        cv.put("code", buyer.code.toString())
        cv.put("password", pref.getPasswordHash())

        db.update("Buyer", cv,"login = ?", arrayOf(buyer.login))
    }

    fun readBuyerCode(): Int {
        val myDatabase = context.let { FeedReaderDbHelper(it).readableDatabase }
        val query = "SELECT id, name, phone, mail, birth, address, code FROM Buyer"
        val m: Array<out String> = arrayOf()
        val mCur = myDatabase.rawQuery(query, m)
        var codeDB = 0
        if(mCur?.moveToFirst() == true) {
            do {
                codeDB = mCur.getString(mCur.getColumnIndex("code")).toInt()

            } while (mCur.moveToNext())
        }
        mCur?.close()
        myDatabase.close()

        return codeDB
    }

    fun updateOrderCode(code: Int) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("code", code.toString())

        db.update("OrderList", cv, "code = ?", arrayOf(0.toString()))
    }

    fun updateBuyerCode(code: Int, login: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("code", code.toString())

        db.update("Buyer", cv, "code = ? AND login = ?", arrayOf(0.toString(), login))
    }

    private fun checkFoodCode(order: Food, code: Int): Boolean {
        val myDatabase = context.let { FeedReaderDbHelper(it).readableDatabase }
        val query = "SELECT id, name, price, code FROM OrderList"
        val m: Array<out String> = arrayOf()
        val mCur = myDatabase.rawQuery(query, m)
        var check = true
        if(mCur?.moveToFirst() == true) {
            do {
                val nameDB = mCur.getString(mCur.getColumnIndex("name"))
                val priceDB = mCur.getString(mCur.getColumnIndex("price"))
                val codeDB = mCur.getString(mCur.getColumnIndex("code")).toInt()

                if (nameDB == order.name && priceDB == order.price.toString() && code == codeDB) {
                    check = false
                    break
                }
            } while (mCur.moveToNext())
        }
        mCur?.close()
        myDatabase.close()

        return check
    }

    fun writePizza (name: String, prices: String, weights: String, ingredients: String) {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put("name", name)
        cv.put("prices", prices)
        cv.put("weights", weights)
        cv.put("ingredient", ingredients)
        cv.put("picture", R.drawable.default_pizza.toString())

        db.insert("Pizza", null, cv)
    }

    fun writeDrink (name: String, prices: String, volumes: String) {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put("name", name)
        cv.put("prices", prices)
        cv.put("volumes", volumes)
        cv.put("picture", R.drawable.default_drink.toString())

        db.insert("Drink", null, cv)
    }

    fun writeDessert (name: String, price: String, weight: String, count: String, ingredients: String) {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put("name", name)
        cv.put("price", price)
        cv.put("weight", weight)
        cv.put("count", count)
        cv.put("ingredient", ingredients)
        cv.put("picture", R.drawable.default_dessert.toString())

        db.insert("Dessert", null, cv)
    }

    fun writeOrderList (orderList: ArrayList<Order>) {
        val db = this.writableDatabase
        val cv = ContentValues()

        for (order in orderList) {
            cv.put("name", order.name)
            cv.put("price", order.price)
            cv.put("count", order.counter)
            cv.put("picture", order.picture)

            db.insert("CompleteOrders", null, cv)
        }
    }

    fun readSeller(login: String): Seller {
        val seller = Seller()
        val myDatabase = context.let { FeedReaderDbHelper(it).readableDatabase }
        val query = "SELECT id, name, phone, mail, login, password FROM Seller WHERE login = ?"
        val m: Array<out String> = arrayOf(login)
        val mCur = myDatabase.rawQuery(query, m)
        if(mCur?.moveToFirst() == true) {
            do {
                seller.name = mCur.getString(mCur.getColumnIndex("name"))
                seller.phone = mCur.getString(mCur.getColumnIndex("phone"))
                seller.mail = mCur.getString(mCur.getColumnIndex("mail"))
                seller.login = mCur.getString(mCur.getColumnIndex("login"))
                seller.password = mCur.getString(mCur.getColumnIndex("password"))
            } while (mCur.moveToNext())
        }
        mCur?.close()
        myDatabase.close()

        return seller
    }

    fun updateSeller (seller: Seller) {
        val pref = PrefManager(context)

        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("name", seller.name)
        cv.put("phone", seller.phone)
        cv.put("mail", seller.mail)
        cv.put("password", pref.getPasswordHash())

        db.update("Seller", cv, "login = ?", arrayOf(pref.getLogin()))
    }

    fun readCompleteOrders(): ArrayList<Order> {
        val orders: ArrayList<Order> = arrayListOf()
        val myDatabase = context.let { FeedReaderDbHelper(it).readableDatabase }
        val query = "SELECT id, name, price, count, picture FROM CompleteOrders"
        val m: Array<out String> = arrayOf()
        val mCur = myDatabase.rawQuery(query, m)

        if(mCur?.moveToFirst() == true) {
            var index = 0
            do {
                val name = mCur.getString(mCur.getColumnIndex("name"))
                val price = mCur.getString(mCur.getColumnIndex("price"))
                val count = mCur.getString(mCur.getColumnIndex("count"))
                val picture = mCur.getString(mCur.getColumnIndex("picture"))
                var pic = 0
                if (context.resources?.getIdentifier(picture, "drawable", context.packageName) != null)
                    pic = context.resources?.getIdentifier(picture, "drawable", context.packageName)!!

                val p = Order(index, name, pic, price.toFloat(), count.toInt(), 0)
                var check = true
                for (item in orders) {
                    if (item.name == p.name) {
                        item.counter += p.counter
                        check = false
                    }
                }
                if (check) {
                    orders.add(p)
                    index += 1
                }
            } while (mCur.moveToNext())
        }
        mCur?.close()
        myDatabase.close()
        return orders
    }

    fun deleteCompleteOrders(orders: ArrayList<Order>) {
        val db = this.writableDatabase
        for (order in orders) {
            db.delete(
                "CompleteOrders", "name = ?", arrayOf(order.name)
            )
        }
    }

    fun readPizza(): ArrayList<Pizza> {
        val listPizza: ArrayList<Pizza> = ArrayList()
        val myDatabase = context.let { FeedReaderDbHelper(it).readableDatabase }
        val query = "SELECT id, name, prices, weights, ingredient, picture FROM Pizza"
        val m: Array<out String> = arrayOf()
        val mCur = myDatabase.rawQuery(query, m)

        if(mCur?.moveToFirst() == true) {
            var index = 0
            do {
                val name = mCur.getString(mCur.getColumnIndex("name"))
                val prices = mCur.getString(mCur.getColumnIndex("prices"))
                val weights = mCur.getString(mCur.getColumnIndex("weights"))
                val ingredient = mCur.getString(mCur.getColumnIndex("ingredient"))
                val picture = mCur.getString(mCur.getColumnIndex("picture"))
                var pic = 0
                if (context.resources?.getIdentifier(picture, "drawable", context.packageName) != null)
                    pic = context.resources?.getIdentifier(picture, "drawable", context.packageName)!!

                val p = Pizza(index, name, prices, weights, ingredient, pic)
                index += 1
                listPizza.add(p)
            } while (mCur.moveToNext())
        }
        mCur?.close()
        myDatabase.close()

        return listPizza
    }

    fun readDrink(): ArrayList<Drink> {
        val listDrink = ArrayList<Drink>()
        val myDatabase = context.let { FeedReaderDbHelper(it).readableDatabase }
        val query = "SELECT id, name, prices, volumes, picture FROM Drink"
        val m: Array<out String> = arrayOf()
        val mCurD = myDatabase.rawQuery(query, m)

        if(mCurD?.moveToFirst() == true) {
            var index = 0
            do {
                val name = mCurD.getString(mCurD.getColumnIndex("name"))
                val prices = mCurD.getString(mCurD.getColumnIndex("prices"))
                val volumes = mCurD.getString(mCurD.getColumnIndex("volumes"))
                val picture = mCurD.getString(mCurD.getColumnIndex("picture"))
                var pic = 0
                if (context.resources?.getIdentifier(picture, "drawable", context.packageName) != null)
                    pic = context.resources?.getIdentifier(picture, "drawable", context.packageName)!!
                val p = Drink(index, name, prices, volumes, pic
                )
                index += 1
                listDrink.add(p)
            } while (mCurD.moveToNext())
        }
        mCurD?.close()
        myDatabase.close()

        return listDrink
    }

    fun readDessert(): ArrayList<Dessert> {
        val listDessert: ArrayList<Dessert> = ArrayList()
        val myDatabase = context.let { FeedReaderDbHelper(it).readableDatabase }
        val query = "SELECT id, name, price, weight, count, ingredient, picture FROM Dessert"
        val m: Array<out String> = arrayOf()
        val mCur = myDatabase.rawQuery(query, m)

        if(mCur?.moveToFirst() == true) {
            var index = 0
            do {
                val name = mCur.getString(mCur.getColumnIndex("name"))
                val price = mCur.getString(mCur.getColumnIndex("price"))
                val weight = mCur.getString(mCur.getColumnIndex("weight"))
                val count = mCur.getString(mCur.getColumnIndex("count"))
                val ingredient = mCur.getString(mCur.getColumnIndex("ingredient"))
                val picture = mCur.getString(mCur.getColumnIndex("picture"))
                var pic = 0
                if (context.resources?.getIdentifier(picture, "drawable", context.packageName) != null)
                    pic = context.resources?.getIdentifier(picture, "drawable", context.packageName)!!

                val p = Dessert(index, name, price, weight, count, ingredient, pic
                )
                index += 1
                listDessert.add(p)
            } while (mCur.moveToNext())
        }
        mCur?.close()
        myDatabase.close()

        return listDessert
    }

    fun readBuyer(login: String): Buyer {
        val buyer = Buyer()
        val myDatabase = context.let { FeedReaderDbHelper(it).readableDatabase }
        val query = "SELECT id, name, phone, mail, birth, address, code, login, password FROM Buyer WHERE login = ?"
        val m: Array<out String> = arrayOf(login)
        val mCur = myDatabase.rawQuery(query, m)

        if (mCur?.moveToFirst() == true) {
            do {
                buyer.name = mCur.getString(mCur.getColumnIndex("name"))
                buyer.phone = mCur.getString(mCur.getColumnIndex("phone"))
                buyer.mail = mCur.getString(mCur.getColumnIndex("mail"))
                buyer.birth = mCur.getString(mCur.getColumnIndex("birth"))
                buyer.address = mCur.getString(mCur.getColumnIndex("address"))
                buyer.code = mCur.getString(mCur.getColumnIndex("code")).toInt()
                buyer.login = mCur.getString(mCur.getColumnIndex("login"))
                buyer.password = mCur.getString(mCur.getColumnIndex("password"))
            } while (mCur.moveToNext())
        }
        mCur?.close()
        myDatabase.close()

        return buyer
    }

    fun writeBuyer (buyer: Buyer) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("name", buyer.name)
        cv.put("phone", buyer.phone)
        cv.put("mail", buyer.mail)
        cv.put("birth", buyer.birth)
        cv.put("address", buyer.address)
        cv.put("code", buyer.code.toString())
        cv.put("login", buyer.login)
        cv.put("password", buyer.password)

        db.insert("Buyer", null, cv)
    }

    fun writeSeller (seller: Seller) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("name", seller.name)
        cv.put("phone", seller.phone)
        cv.put("mail", seller.mail)
        cv.put("login", seller.login)
        cv.put("password", seller.password)

        db.insert("Seller", null, cv)
    }

    fun writePizzaLog (log: PizzaLog) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("time", log.time)
        cv.put("login", log.login)
        cv.put("action", log.action)

        db.insert("Log", null, cv)
    }

    fun readPizzaLogs(): ArrayList<PizzaLog> {
        val logs = ArrayList<PizzaLog>()
        val myDatabase = context.let { FeedReaderDbHelper(it).readableDatabase }
        val query = "SELECT id, time, login, action FROM Log"
        val m: Array<out String> = arrayOf()
        val mCurD = myDatabase.rawQuery(query, m)

        if(mCurD?.moveToFirst() == true) {
            var index = 0
            do {
                val time = mCurD.getString(mCurD.getColumnIndex("time"))
                val login = mCurD.getString(mCurD.getColumnIndex("login"))
                val action = mCurD.getString(mCurD.getColumnIndex("action"))
                val p = PizzaLog(time, login, action)
                index += 1
                logs.add(p)
            } while (mCurD.moveToNext())
        }
        mCurD?.close()
        myDatabase.close()

        return logs
    }
}