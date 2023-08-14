package com.kisusyenni.disasterapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kisusyenni.disasterapp.data.FakeApiService
import com.kisusyenni.disasterapp.data.api.ApiService
import com.kisusyenni.disasterapp.utils.CoroutinesTestRule
import com.kisusyenni.disasterapp.utils.DummyData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(AndroidJUnit4::class)
class DisasterAppRepositoryImplTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Inject
    lateinit var apiService: ApiService

    private lateinit var disasterAppRepository: DisasterAppRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Before
    fun setUp() {
        apiService = FakeApiService()
        disasterAppRepository = DisasterAppRepositoryImpl(apiService)
    }

    private val dummyReportsResponse = DummyData.generateReports()

    @Test
    fun `When get reports response should not null with response`() = runTest {
        disasterAppRepository.getReports("flood", "ID-JT").collect { result ->
            Assert.assertNotNull(result)
            Assert.assertEquals(
                dummyReportsResponse,
                result
            )
        }
    }
}