package com.wkitinerary.ui.tripdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wkitinerary.domain.Trip
import com.wkitinerary.domain.usecase.GetTripUseCase
import com.wkitinerary.utils.CustomDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripDetailViewModel @Inject constructor(
    private val getTripUseCase: GetTripUseCase,
    private val dispatcher: CustomDispatcher
): ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()

    fun getTrip(id: Long?) {
        viewModelScope.launch(dispatcher.io()) {
            getTripUseCase(id).collect {
                _uiState.value = UiState.Success(it)
            }
        }
    }

    sealed interface UiState {
        object Loading : UiState
        data class Success(val trip: Trip): UiState
    }
}
