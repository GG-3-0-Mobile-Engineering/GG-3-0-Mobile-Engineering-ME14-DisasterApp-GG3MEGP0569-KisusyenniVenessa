package com.kisusyenni.disasterapp.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
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
import com.google.android.material.search.SearchView.TransitionState
import com.kisusyenni.disasterapp.R
import com.kisusyenni.disasterapp.data.api.ReportsGeometriesItem
import com.kisusyenni.disasterapp.data.api.ReportsResponse
import com.kisusyenni.disasterapp.databinding.ActivityMainBinding
import com.kisusyenni.disasterapp.utils.Area
import com.kisusyenni.disasterapp.utils.AreaHelper.areaList
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
                    val visible = View.VISIBLE
                    val gone = View.GONE

                    // Show slide down button if bottom sheet is expanded
                    binding.slideDownBtn.visibility = if(state == BottomSheetBehavior.STATE_EXPANDED) visible else gone

                    // Hide drag handle if bottom sheet is expanded
                    binding.dragHandle.visibility = if(state == BottomSheetBehavior.STATE_EXPANDED) gone else visible

                    // When bottom sheet is expanded, unable to drag
                    isDraggable = (state != BottomSheetBehavior.STATE_EXPANDED)
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if (slideOffset > originHeight) {
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

    private fun setCoordinatesData(geos: List<ReportsGeometriesItem?>?) {
        if (!geos.isNullOrEmpty()) {
            for (place in geos.withIndex()) {

                val lat: Double = place.value?.coordinates?.get(1) ?: 0.0
                val lng: Double = place.value?.coordinates?.get(0) ?: 0.0

                val area = LatLng(lat, lng)

                mMap.addMarker(
                    MarkerOptions()
                        .position(area)
                        .title(place.value?.properties?.pkey)
                )

                if (place.index == 0) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(area, 12F))
                }
            }
        }
    }

    private fun getData(disaster: String = resources.getString(R.string.flood)) {
        viewModel.getReports(disaster)
    }

    private fun getData(disaster: String = resources.getString(R.string.flood), admin: String) {
        viewModel.getReports(disaster, admin)
    }

    private fun observeReports() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.reports.collect { state ->
                    when (state) {
                        is UiState.Empty -> {
                            println("Disaster Data: Empty")
                        }

                        is UiState.Loading -> {
                            println("Disaster Data: Loading")
                        }

                        is UiState.Success<*> -> {
                            setDisasterList(state.result as ReportsResponse)
                            setCoordinatesData(state.result.result?.objects?.output?.geometries)
                        }

                        is UiState.Failure -> {
                            println("Disaster Data: Failed")
                        }
                    }

                }

            }
        }
    }

    private fun setUpSearchBar() {
        val searchBar = binding.searchBar
        searchBar.inflateMenu(R.menu.searchbar_menu)

        searchBar.setOnMenuItemClickListener { menuItem: MenuItem? ->
            when (menuItem?.itemId) {
                R.id.settings -> {
                    intentToSettings()
                    true
                }

                else -> true
            }
        }

    }

    private fun setUpSearchView() {
        val searchView = binding.searchView
        val searchBar = binding.searchBar
        val bottomSheetLayout = binding.disasterListCoordinatior

        searchView.setupWithSearchBar(searchBar)

        searchView.addTransitionListener { _, _, newState ->

            if (newState === TransitionState.SHOWING) {
                // Handle search view opened.
                bottomSheetLayout.visibility = View.GONE
                binding.searchBar.visibility = View.GONE
            } else if (newState === TransitionState.HIDING) {
                binding.searchBar.visibility = View.VISIBLE
                bottomSheetLayout.visibility = View.VISIBLE
            }
        }

        setDisasterAreaList(areaList)

        searchView
            .editText
            .addTextChangedListener (object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val keyword = p0.toString()
                    filterAreaList(keyword)
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
    }

    private fun filterAreaList(keyword: String){
        val filtered = if(keyword.isNotEmpty()) {
            areaList.filter { it.province
                .lowercase()
                .replace("\\s".toRegex(), "")
                .contains(keyword.replace("\\s".toRegex(), "")) }
        } else {
            areaList
        }
        setDisasterAreaList(filtered)
    }

    private fun intentToSettings() {
        Intent(this, SettingsActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun setUpChipGroup() {
        var disaster = findDisasterName(binding.disasterChipGroup.checkedChipId)
        getData(disaster)

        binding.disasterChipGroup.setOnCheckedStateChangeListener { group, _ ->
            disaster = findDisasterName(group.checkedChipId)
            getData(disaster)
        }
    }

    private fun findDisasterName(checkedId: Int): String {
        val position = binding.disasterChipGroup.children.indexOfFirst { it.id == checkedId }
        return resources.getStringArray(R.array.disaster_types)[position].lowercase()
    }

    private fun setDisasterAreaList(area: List<Area>) {
        val areaListAdapter = AreaListAdapter(area)
        binding.rvArea.apply {
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = areaListAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)

        }
        areaListAdapter.apply {
            notifyDataSetChanged()
            setOnAreaClickCallback(object : AreaListAdapter.OnAreaClickCallback {
                override fun onItemClicked(area: Area, position: Int) {
                    setSearchBarText(area.province)
                    getData(admin = area.code)
                }

            })
        }
    }

    private fun setSearchBarText(area: String) {
        binding.searchBar.text = area
        binding.searchView.hide()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(binding.map.id) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setUpSearchBar()
        setUpSearchView()
    }

    // Set Google maps
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        setUpChipGroup()
        observeReports()
        showDisasterListBottomSheet()
    }

}