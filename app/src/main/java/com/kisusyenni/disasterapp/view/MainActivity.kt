package com.kisusyenni.disasterapp.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.children
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
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
import com.kisusyenni.disasterapp.utils.NotificationHelper
import com.kisusyenni.disasterapp.utils.ToastHelper.setToastShort
import com.kisusyenni.disasterapp.utils.UiState
import com.kisusyenni.disasterapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: MainViewModel

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
                    binding.slideDownBtn.visibility =
                        if (state == BottomSheetBehavior.STATE_EXPANDED) visible else gone

                    // Hide drag handle if bottom sheet is expanded
                    binding.dragHandle.visibility =
                        if (state == BottomSheetBehavior.STATE_EXPANDED) gone else visible

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

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
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
        mMap.clear()
        if (!geos.isNullOrEmpty()) {
            setToastShort(this@MainActivity, resources.getString(R.string.success_load_data))

            for (place in geos.withIndex()) {

                val lat: Double = place.value?.coordinates?.get(1) ?: 0.0
                val lng: Double = place.value?.coordinates?.get(0) ?: 0.0

                val area = LatLng(lat, lng)

                mMap.addMarker(
                    MarkerOptions()
                        .position(area)
                        .title(place.value?.properties?.pkey)
                )

                val zoom = if (geos.size > 10) 10F else 15F

                if (place.index == 0) {
                    lifecycleScope.launch {
                        delay(1000L)
                        mMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(area, zoom)
                        )
                    }
                }
            }
        } else {
            setToastShort(this@MainActivity, resources.getString(R.string.empty_disaster_data))
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
                            binding.pbDisaster.visibility = View.VISIBLE
                            binding.pbMap.visibility = View.VISIBLE
                            binding.tvDisasterState.visibility = View.GONE
                        }

                        is UiState.Loading -> {
                            binding.pbDisaster.visibility = View.VISIBLE
                            binding.pbMap.visibility = View.VISIBLE
                            binding.tvDisasterState.visibility = View.GONE
                        }

                        is UiState.Success<*> -> {
                            setDisasterList(state.result as ReportsResponse)
                            setCoordinatesData(state.result.result?.objects?.output?.geometries)
                            binding.pbDisaster.visibility = View.GONE
                            binding.pbMap.visibility = View.GONE
                            binding.tvDisasterState.visibility = View.GONE
                        }

                        is UiState.Failure -> {
                            state.e.message?.let {
                                setToastShort(this@MainActivity, it)
                                binding.tvDisasterState.apply {
                                    title = it
                                    visibility = View.VISIBLE
                                }
                            }
                            binding.pbDisaster.visibility = View.GONE
                            binding.pbMap.visibility = View.GONE

                        }
                    }

                }

            }
        }
    }

    /*
        Filter Functionality for Filter Disaster Type
    */
    private fun setUpTypeChipGroup() {
        var disaster = findDisasterName(binding.disasterChipGroup.checkedChipId)
        viewModel.setDisasterType(disaster)

        binding.disasterChipGroup.setOnCheckedStateChangeListener { group, _ ->
            disaster = findDisasterName(group.checkedChipId)
            viewModel.setDisasterType(disaster)
        }
    }

    private fun findDisasterName(checkedId: Int): String {
        val position = binding.disasterChipGroup.children.indexOfFirst { it.id == checkedId }
        return resources.getStringArray(R.array.disaster_types)[position].lowercase()
    }

    /*
        Search Functionality for Filter Area
    */

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

        // Setup search view and search bar
        searchView.setupWithSearchBar(searchBar)

        // Hide views when search view is showing
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

        // When user typing area
        searchView
            .editText
            .addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val keyword = p0.toString()

                    // If keyword is not empty, return filtered area based on keyword, else set all area list
                    val filtered = if (keyword.isNotEmpty()) {
                        areaList.filter {
                            it.province
                                .lowercase()
                                .replace("\\s".toRegex(), "")
                                .contains(
                                    keyword
                                        .lowercase()
                                        .replace("\\s".toRegex(), "")
                                )
                        }
                    } else {
                        areaList
                    }

                    viewModel.setAreaData(filtered)

                    // If keyword is empty set admin view model data and search bar text to null
                    if (keyword.isEmpty()) {
                        viewModel.setAdmin(null)
                        searchBar.text = null
                    }

                    // If area list is not found show text to imply data is empty
                    binding.tvAreaState.visibility =
                        if (filtered.isEmpty()) View.VISIBLE else View.GONE
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
    }

    private fun intentToSettings() {
        Intent(this, SettingsActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun setDisasterAreaList(area: List<Area>) {
        val areaListAdapter = AreaListAdapter()
        binding.rvArea.apply {
//            addItemDecoration(
//                DividerItemDecoration(
//                    this.context,
//                    DividerItemDecoration.VERTICAL
//                )
//            )
            adapter = areaListAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)

        }

        areaListAdapter.apply {
            submitList(area)
            notifyDataSetChanged()
            setOnAreaClickCallback(object : AreaListAdapter.OnAreaClickCallback {
                override fun onItemClicked(area: Area, position: Int) {
                    setSearchBarText(area.province)
                    viewModel.setAdmin(area)
                }

            })
        }
    }

    private fun setSearchBarText(area: String) {
        binding.searchBar.text = area
        binding.searchView.hide()
    }

    private fun observeDisasterAreaList() {
        viewModel.setAreaData(areaList)
        viewModel.areaData.observe(this) {
            setDisasterAreaList(it)
        }
    }

    /*
    * Observe disaster type selection and search area changed for get data
    * */

    private fun observeDisasterTypeAndArea() {

        viewModel.disasterType.observe(this) { type ->
            if (viewModel.getAdmin() == null) {
                getData(disaster = type)
            }

            viewModel.admin.observe(this) { area ->
                if (area != null) {
                    getData(disaster = type, admin = area.code)
                }
            }

            createNotification(type)
        }
    }

    private fun createNotification(type: String) {
        val service = NotificationHelper(applicationContext)
        val isTypeSame = type.lowercase() == resources.getString(R.string.flood).lowercase()
        if (isTypeSame) {
            service.showNotification(
                title = resources.getString(R.string.water_level_alert),
                message = resources.getString(R.string.water_level_alert_desc)
            )
        }
    }

    /*
    * Observe app theme from datastore
    * */

    private fun observeTheme() {
        viewModel.getTheme()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isDarkMode.collect { isDarkMode ->
                    if (isDarkMode) {
                        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this@MainActivity, R.raw.maps_dark_mode))
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this@MainActivity, R.raw.maps_light_mode))
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()

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

        setUpTypeChipGroup()
        observeReports()
        observeDisasterTypeAndArea()
        observeDisasterAreaList()
        showDisasterListBottomSheet()
        observeTheme()
    }

}