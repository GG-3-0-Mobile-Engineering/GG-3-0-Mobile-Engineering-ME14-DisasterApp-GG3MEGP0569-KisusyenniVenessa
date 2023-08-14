package com.kisusyenni.disasterapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kisusyenni.disasterapp.data.SettingsPreference
import com.kisusyenni.disasterapp.data.repository.DisasterAppRepository
import com.kisusyenni.disasterapp.utils.CoroutinesTestRule
import com.kisusyenni.disasterapp.utils.UiState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.robolectric.annotation.Config
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(AndroidJUnit4::class)
class MainViewModelTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Inject
    @Mock
    lateinit var disasterAppRepository: DisasterAppRepository

    @Inject
    @Mock
    lateinit var settingsPreference: SettingsPreference

    private lateinit var viewModel: MainViewModel

    @Before
    fun init() {
        hiltRule.inject()
        viewModel = MainViewModel(disasterAppRepository, settingsPreference)
    }

    @Test
    fun `When get reports should not null`() = runTest {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.reports.collect {state ->
                when (state) {
                    is UiState.Success<*> -> {
                        Assert.assertNotNull(state.result)
                        Mockito.verify(disasterAppRepository).getReports("flood")
                    }
                    else -> {}
                }
            }

        }
    }
}