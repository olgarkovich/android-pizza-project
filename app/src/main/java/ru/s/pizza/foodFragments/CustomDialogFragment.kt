package ru.s.pizza.foodFragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.s.pizza.MainActivity
import ru.s.pizza.seller.SellerActivity


class CustomDialogFragment(var type: String) : DialogFragment() {

    private val catNames = arrayOf("Покупатель", "Владелец")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            var selectedItems = 0 // Where we track the selected items
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Выберите категорию:").setSingleChoiceItems(catNames, -1) {
                dialog, item ->
                Toast.makeText(activity, "${catNames[item]}", Toast.LENGTH_SHORT).show()
                selectedItems = item
            }.setPositiveButton("OK") {
                dialog, id ->
                if (catNames[selectedItems] == "Покупатель" && catNames[selectedItems] != type) {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }
                if (catNames[selectedItems] == "Владелец" && catNames[selectedItems] != type) {
                    val  intent = Intent(context, SellerActivity::class.java)
                    startActivity(intent)
                }
            }.setNegativeButton("Отмена") {
                dialog, id ->
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
