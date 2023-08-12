package com.wkitinerary.ui.tripdetail

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wkitinerary.domain.Trip
import com.wkitinerary.ui.tripdetail.TripDetailViewModel.UiState.Loading
import com.wkitinerary.ui.tripdetail.TripDetailViewModel.UiState.Success
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TripDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: TripDetailViewModel = hiltViewModel(),
    id: Long?
) {
    val uiState = viewModel.uiState.collectAsState().value
    LaunchedEffect(id) {
        viewModel.getTrip(id)
    }

    when (uiState) {
        Loading -> Text(text = "Loading...")
        is Success -> TripDetail(trip = uiState.trip, modifier = modifier)
    }
}

@Composable
fun TripDetail(trip: Trip, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = trip.title,
            color = Color.White,
            modifier = Modifier.padding(20.dp)
        )
        LazyColumn {
            itemsIndexed(trip.dates) {idx, it ->
                Divider()
                Activities(date = it)
                if(idx == trip.dates.lastIndex) Divider()
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Activities(date: Date) {
    var expanded by remember { mutableStateOf(false) }
    AnimatedContent(
        targetState = expanded,
        transitionSpec = {
            fadeIn(animationSpec = tween(150, 150)) with
                    fadeOut(animationSpec = tween(150, 150)) using
                    SizeTransform { initialSize, targetSize ->
                        if (targetState) {
                            keyframes {
                                // Expand horizontally first.
                                IntSize(targetSize.width, initialSize.height) at 150
                                durationMillis = 300
                            }
                        } else {
                            keyframes {
                                // Shrink vertically first.
                                IntSize(initialSize.width, targetSize.height) at 150
                                durationMillis = 300
                            }
                        }
                    }

        }
    ) { isExpanded ->
        if (!isExpanded) {
            DateDetail(date = date) { expanded = !expanded }
        } else {
            AddActivity(date = date) { expanded = !expanded }
        }
    }
}

@Composable
fun DateDetail(date: Date, onClick: () -> Unit) {
    Row(
        Modifier
            .height(intrinsicSize = IntrinsicSize.Min)
            .clickable { onClick() }) {
        val sdf = SimpleDateFormat("dd/MM", Locale.ROOT)
        val formattedDate = sdf.format(date)
        Text(
            modifier = Modifier.wrapContentSize(),
            text = formattedDate,
            color = Color.White
        )
        Divider(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
        )
        Box(
            modifier = Modifier
                .height(75.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun AddActivity(date: Date, onClick: () -> Unit) {
    Column {
        val sdf = SimpleDateFormat("dd/MM", Locale.ROOT)
        val formattedDate = sdf.format(date)
        Divider()
        Text(
            modifier = Modifier
                .clickable { onClick() }
                .padding(10.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            text = formattedDate,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Divider()
        repeat(24) {
            Text(
                text = String.format("%02d:00", it),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = Color.White
            )
            Divider()
        }
    }
}

@Preview
@Composable
fun Trip() {
    TripDetail(trip = Trip(0L, "Test trip", 0, Date(), Date(), listOf(Date(), Date(), Date())))
}
