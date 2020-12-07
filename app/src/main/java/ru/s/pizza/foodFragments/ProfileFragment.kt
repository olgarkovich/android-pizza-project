package ru.s.pizza.foodFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.s.pizza.ProfileChangeActivity
import ru.s.pizza.R
import ru.s.pizza.models.FeedReaderDbHelper
import ru.s.pizza.models.person.Buyer
import kotlin.random.Random

class ProfileFragment : Fragment() {

    var buyer = Buyer()
    lateinit var name: TextView
    lateinit var phone: TextView
    lateinit var mail: TextView
    lateinit var birth: TextView
    lateinit var address: TextView
    lateinit var message: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        name = view.findViewById(R.id.name_profile_text)
        phone = view.findViewById(R.id.phone_profile_text)
        mail = view.findViewById(R.id.mail_profile_text)
        birth = view.findViewById(R.id.birth_profile_text)
        address = view.findViewById(R.id.address_profile_text)
        message = view.findViewById(R.id.message_profile)
        val changeProfile = view?.findViewById<Button>(R.id.change_profile_button)
        val anotherProfile = view?.findViewById<Button>(R.id.another_profile)

        val myDatabase = context?.let { FeedReaderDbHelper(it).readableDatabase }
        val query = "SELECT id, name, phone, mail, birth, address, code FROM Buyer"
        val m: Array<out String> = arrayOf()
        val mCur = myDatabase?.rawQuery(query, m)

        var codeDB = 0
        if(mCur?.moveToFirst() == true) {
            do {
                val nameDB = mCur.getString(mCur.getColumnIndex("name"))
                val phoneDB = mCur.getString(mCur.getColumnIndex("phone"))
                val mailDB = mCur.getString(mCur.getColumnIndex("mail"))
                val birthDB = mCur.getString(mCur.getColumnIndex("birth"))
                val addressDB = mCur.getString(mCur.getColumnIndex("address"))
                codeDB = mCur.getString(mCur.getColumnIndex("code")).toInt()

                if (nameDB != "null") name.text = nameDB
                if (phoneDB != "null") phone.text = phoneDB
                if (mailDB != "null") mail.text = mailDB
                if (birthDB != "null") birth.text = birthDB
                if (addressDB != "null") address.text = addressDB

            } while (mCur.moveToNext())
        }
        mCur?.close()
        myDatabase?.close()

        if (name.text == "" || phone.text == "" || mail.text == "" ||
            birth.text == "" || address.text == "") {
            message.visibility = View.VISIBLE
            codeDB = Random.nextInt(1, 1000)
            buyer = Buyer(
                name.text.toString(), phone.text.toString(), mail.text.toString(),
                birth.text.toString(), address.text.toString(), codeDB
            )
            val db = context?.let { FeedReaderDbHelper(it) }
            db?.updateOrderCode(codeDB)
            db?.updateBuyerCode(codeDB)
        }
        else {
            message.visibility = View.INVISIBLE
            buyer = Buyer(
                name.text.toString(), phone.text.toString(), mail.text.toString(),
                birth.text.toString(), address.text.toString(), codeDB
            )
        }

        changeProfile?.setOnClickListener {
            val intent = Intent(context, ProfileChangeActivity::class.java)
            intent.putExtra("Buyer", buyer)
            startActivityForResult(intent,1)
        }

        anotherProfile?.setOnClickListener {
            val dialogFragment = CustomDialogFragment("Покупатель")
            fragmentManager?.let { dialogFragment.show(it, "ssss") }
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) {return}
        val buyer = data.getParcelableExtra<Buyer>("Buyer")!!
        name.text = buyer.name
        phone.text = buyer.phone
        mail.text = buyer.mail
        birth.text = buyer.birth
        address.text = buyer.address
        message.visibility = View.INVISIBLE
    }
}
