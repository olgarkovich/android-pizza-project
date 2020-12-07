package ru.s.pizza.foodFragments.food

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import ru.s.pizza.MyPagerAdapter
import ru.s.pizza.R
import ru.s.pizza.serverWork.Server


class FoodFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_food, container, false)
        val context = requireContext().applicationContext
        val viewPager = view.findViewById<ViewPager>(R.id.viewPager)
        val tabs = view.findViewById<TabLayout>(R.id.tabs)

        val sr = Server(context)
        if (sr.isOnline) setUpViewPagerInternet(viewPager) else setUpViewPager(viewPager, context)
        tabs.setupWithViewPager(viewPager)

        return view
    }

    private fun setUpViewPager(viewPager: ViewPager, context: Context?) {

        Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show()

        val adapterFood = MyPagerAdapter(childFragmentManager)
        adapterFood.addFragment(PizzaFragment(), "Pizza")
        adapterFood.addFragment(DessertFragment(), "Dessert")
        adapterFood.addFragment(DrinkFragment(), "Drink")

        viewPager.adapter = adapterFood
    }

    private fun setUpViewPagerInternet(viewPager: ViewPager) {

        val adapterFood = MyPagerAdapter(childFragmentManager)
        adapterFood.addFragment(PizzaFragment(), "Pizza")
        adapterFood.addFragment(AdvertisingFragment(), "")
        adapterFood.addFragment(DessertFragment(), "Dessert")
        adapterFood.addFragment(AdvertisingFragment(), "")
        adapterFood.addFragment(DrinkFragment(), "Drink")

        viewPager.adapter = adapterFood
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

//    private fun isOnline(context: Context?): Boolean {
//
//        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//        val n = cm.activeNetwork
//        if (n != null) {
//            val nc = cm.getNetworkCapabilities(n)
//            return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
//                NetworkCapabilities.TRANSPORT_WIFI
//            )
//        }
//        return false
//    }
}


