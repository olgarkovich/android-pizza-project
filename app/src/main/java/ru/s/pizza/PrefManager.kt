package ru.s.pizza

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class PrefManager(context: Context) {

    private val currentLogin = "currentLogin"
    private val currentPassword = "currentPassword"

    private val pref: SharedPreferences = context.getSharedPreferences("PrefPizza", MODE_PRIVATE)

    fun setLogin(login: String) {
        val editor = pref.edit()
        editor.putString(currentLogin, login)
        editor.apply()
    }

    fun getLogin(): String {
        return pref.getString(currentLogin, "login")!!
    }

    fun setPasswordHash(password: String) {
        val editor = pref.edit()
        editor.putString(currentPassword, password)
        editor.apply()
    }

    fun getPasswordHash(): String {
        return pref.getString(currentPassword, "null")!!
    }
}