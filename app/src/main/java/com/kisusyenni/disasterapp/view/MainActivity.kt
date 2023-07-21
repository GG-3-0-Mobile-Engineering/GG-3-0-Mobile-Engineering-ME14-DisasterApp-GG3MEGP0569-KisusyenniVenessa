package com.kisusyenni.disasterapp.view

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kisusyenni.disasterapp.R
import com.kisusyenni.disasterapp.data.api.ReportsResponse
import com.kisusyenni.disasterapp.databinding.ActivityMainBinding
import com.kisusyenni.disasterapp.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private lateinit var mMap: GoogleMap
    private val dummyData = ArrayList<String>()
    private val viewModel by inject<MainViewModel>()

    private fun showDisasterListBottomSheet() {
        // Initialize the bottom sheet behavior
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetContent)
        // Set the bottom sheet to be persistent
        bottomSheetBehavior.isHideable = false
        // Set the bottom sheet state to half expanded
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    private fun setDisasterList(data: ReportsResponse) {
        val disasterListAdapter = DisasterListAdapter()
        binding.rvDisaster.apply {
            adapter = disasterListAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
        disasterListAdapter.submitList(dummyData)
        val result = data.result
    }

    private fun getData() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getData().collect  { data ->
                    setDisasterList(data)
                }

            }
        }
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
        getData()
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