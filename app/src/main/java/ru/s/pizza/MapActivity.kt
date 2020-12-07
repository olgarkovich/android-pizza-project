package ru.s.pizza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : FragmentActivity(), OnMapReadyCallback {

    var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        var mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap
        val place1 = LatLng(52.114109, 26.109208)
        val place2 = LatLng(52.210914, 24.356819)
        val place3 = LatLng(52.199029, 24.018641)
        val place4 = LatLng(52.112622, 23.777346)
        val place5 = LatLng(52.072975, 23.752911)
        val place6 = LatLng(52.094485, 23.689170)
        val place7 = LatLng(52.088018, 23.688578)

        map?.addMarker(MarkerOptions().position(place1).title("Пинск, ул. В.Коржа 12"))
        map?.addMarker(MarkerOptions().position(place2).title("Кобрин, ул. Суворова 7А"))
        map?.addMarker(MarkerOptions().position(place3).title("Жабинка, ул. Ленина 14"))
        map?.addMarker(MarkerOptions().position(place4).title("Брест, ул. Ленинградская 37"))
        map?.addMarker(MarkerOptions().position(place5).title("Брест, ул. Суворова 90"))
        map?.addMarker(MarkerOptions().position(place6).title("Брест, ул. Комсомольская 42/1"))
        map?.addMarker(MarkerOptions().position(place7).title("Брест, ул. К.Маркса 77/1"))
        map?.moveCamera(CameraUpdateFactory.newLatLng(place1))
    }
}
