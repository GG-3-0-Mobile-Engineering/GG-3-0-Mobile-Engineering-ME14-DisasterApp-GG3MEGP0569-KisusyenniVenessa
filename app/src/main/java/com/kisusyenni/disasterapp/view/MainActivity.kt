package com.kisusyenni.disasterapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kisusyenni.disasterapp.R
import com.kisusyenni.disasterapp.databinding.ActivityMainBinding
import com.kisusyenni.disasterapp.databinding.BottomSheetBinding

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var dialog: BottomSheetDialog
    private lateinit var binding: ActivityMainBinding
    private lateinit var dialogView: BottomSheetBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var mMap: GoogleMap
    private val dummyData = ArrayList<String>()
    private fun showDisasterListBottomSheet() {
        dialogView = BottomSheetBinding.inflate(layoutInflater, null, false)
        dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView.root)
        bottomSheetBehavior = BottomSheetBehavior.from(dialogView.root)
        dialog.show()
        dialog.behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    private fun setDisasterList() {
        val disasterListAdapter = DisasterListAdapter()
        dialogView.apply {
            rvDisaster.apply {
                adapter = disasterListAdapter
                layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
            }
        }

        disasterListAdapter.submitList(dummyData)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(binding.map.id) as SupportMapFragment
        mapFragment.getMapAsync(this)

        for(i in 1..10) {
            dummyData.add(resources.getString(R.string.example, i) )
        }
        showDisasterListBottomSheet()
        setDisasterList()
    }

    // Set Google maps
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(
            MarkerOptions()
            .position(sydney)
            .title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

}