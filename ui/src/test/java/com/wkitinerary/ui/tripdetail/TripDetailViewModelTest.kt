package com.wkitinerary.ui.tripdetail

import com.wkitinerary.domain.Trip
import com.wkitinerary.domain.usecase.GetTripUseCase
import com.wkitinerary.utils.CustomDispatcher
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class TripDetailViewModelTest {

    @MockK
    private lateinit var dispatcher: CustomDispatcher

    @MockK
    private lateinit var useCase: GetTripUseCase

    private lateinit var viewModel: TripDetailViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = TripDetailViewModel(useCase, dispatcher)
    }

    @Test
    fun foo() {
        val id = 1L
        val trip = Trip(
            id = 0,
            title = "",
            image = 0,
            departureDate = Date(),
            returnDate = Date(),
            dates = listOf()
        )
        every { dispatcher.io() } returns UnconfinedTestDispatcher()
        coEvery { useCase.invoke(id) } returns flowOf(trip)

        viewModel.getTrip(id)

        assertEquals(trip, (viewModel.uiState.value as TripDetailViewModel.UiState.Success).trip)
    }
}