package com.wkitinerary.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wkitinerary.domain.Trip
import com.wkitinerary.domain.usecase.AddTripUseCase
import com.wkitinerary.domain.usecase.GetAllTripUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllTripUseCase: GetAllTripUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState

    fun getTrip() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getAllTripUseCase().collect {
                    val items: MutableList<HomeItems> =
                        it.map { trip ->
                            HomeItems.Trip(
                                trip.title,
                                trip.image,
                                trip.departureDate,
                                trip.returnDate
                            )
                        }
                            .toMutableList()
                    items.add(HomeItems.AddTrip)

                    _uiState.value = UiState.Success(items)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Empty
            }
        }
    }

    sealed interface UiState {
        data class Success(val trips: List<HomeItems>) : UiState
        object Empty : UiState
    }

}