package com.kisusyenni.disasterapp.view

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.kisusyenni.disasterapp.data.api.ReportsResponse
import com.kisusyenni.disasterapp.databinding.ActivityMainBinding
import com.kisusyenni.disasterapp.utils.UiState
import com.kisusyenni.disasterapp.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private lateinit var mMap: GoogleMap
    private val viewModel by inject<MainViewModel>()

    private fun showDisasterListBottomSheet() {
        val originHeight = (0.3f * resources.displayMetrics.heightPixels).toInt()
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetContent)
        bottomSheetBehavior.apply {
            isHideable = false
            peekHeight = originHeight
            state = BottomSheetBehavior.STATE_COLLAPSED

            addBottomSheetCallback(object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if(state == BottomSheetBehavior.STATE_EXPANDED) {
                        binding.slideDownBtn.visibility = View.VISIBLE
                        binding.dragHandle.visibility = View.GONE
                        isDraggable = false
                    } else {
                        binding.slideDownBtn.visibility = View.GONE
                        binding.dragHandle.visibility = View.VISIBLE
                        isDraggable = true
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if(slideOffset > originHeight) {
                        state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }

            })
        }

        binding.slideDownBtn.setOnClickListener {
            bottomSheetBehavior.apply {
                state = BottomSheetBehavior.STATE_COLLAPSED
                peekHeight = originHeight
            }

        }
    }

    private fun setDisasterList(data: ReportsResponse) {
        val disasterListAdapter = DisasterListAdapter()
        binding.rvDisaster.apply {
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = disasterListAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
        disasterListAdapter.submitList(data.result?.objects?.output?.geometries)
    }

    private fun getData() {

        viewModel.getReports("ID-JK")
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.reports.collect { state ->
                    when(state) {
                        is UiState.Empty -> {
                            println("Empty")
                        }
                        is UiState.Loading -> {
                            println("Loading")
                        }
                        is UiState.Success<*> -> {
                            setDisasterList(state.result as ReportsResponse)
                        }
                        is UiState.Failure -> {
                            println("Failed")
                        }
                    }

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