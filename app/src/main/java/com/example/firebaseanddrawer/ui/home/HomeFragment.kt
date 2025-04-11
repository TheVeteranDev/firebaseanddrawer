package com.example.firebaseanddrawer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseanddrawer.R
import com.example.firebaseanddrawer.databinding.FragmentHomeBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HomeFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Latitude and longitude for PSU's Beaver Stadium
    private val latLong = LatLng(40.812195, -77.856102)

    // Sets the camera zoom factor, 10f = city, 15f = street, 20f = building
    private val zoom = 15f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Using a map view to display the map
        // There was a bug where the app would crash using SupportFragmentMap
        val mapView: MapView = root.findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return root
    }

    override fun onMapReady(map: GoogleMap) {
        with(map) {
            // Set the map camera to the latitude and longitude and the zoom factor
            moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, zoom))

            // Add a pin to the map for Beaver Stadium
            addMarker(MarkerOptions().position(latLong))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}