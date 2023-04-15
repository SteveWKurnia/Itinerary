package com.wkitinerary.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.itinerary.R
import com.wkitinerary.ui.composables.models.Month
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DurationField() {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        CalendarField(Modifier.weight(1f))
        CalendarField(Modifier.weight(1f))
    }
}

@Composable
fun CalendarField(modifier: Modifier) {
    val (date, month, year) = getCurrentDate()
    var dateState by remember { mutableStateOf(date) }
    var monthState by remember { mutableStateOf(month) }
    var yearState by remember { mutableStateOf(year) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier
            .height(250.dp)
            .wrapContentWidth()
            .padding(bottom = 10.dp)
            .border(
                width = 2.dp,
                color = colorResource(id = R.color.purple_500),
                shape = RoundedCornerShape(3.dp)
            )
    ) {
        Text(
            text = "$dateState/$monthState/$yearState",
            color = Color.White,
            modifier = Modifier.padding(top = 5.dp)
        )
        Column(modifier = Modifier.padding(8.dp)) {
            CalendarHeader(monthState, yearState,
                onPrevious = {
                    if (monthState > month || yearState > year) monthState -= 1
                    if (monthState == 0) {
                        monthState = 11
                        yearState -= 1
                    }
                },
                onNext = {
                    if (monthState == 12) {
                        yearState += 1
                        monthState = 1
                    } else {
                        monthState += 1
                    }
                }
            )
            CalendarDate(yearState, Month(monthState)) {
                dateState = it
            }
        }
    }
}

@Composable
private fun CalendarHeader(
    monthState: Int,
    yearState: Int,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    Row(modifier = Modifier.height(20.dp)) {
        Icon(
            Icons.Rounded.KeyboardArrowLeft,
            contentDescription = "previous", tint = Color.White,
            modifier = Modifier.clickable { onPrevious() }
        )

        Spacer(modifier = Modifier.weight(1f))
        CalendarMonth(monthState)
        CalendarYear(yearState = yearState)
        Spacer(modifier = Modifier.weight(1f))

        Icon(
            Icons.Rounded.KeyboardArrowRight,
            contentDescription = "previous", tint = Color.White,
            modifier = Modifier.clickable { onNext() }
        )
    }
}

@Composable
fun CalendarMonth(monthState: Int) {
    val monthName = DateFormatSymbols().months[monthState - 1]
    Text(text = monthName, color = Color.White)
}

@Composable
fun CalendarYear(yearState: Int) {
    Text(text = yearState.toString(), color = Color.White)
}

@Composable
private fun CalendarDate(year: Int, month: Month, block: (Int) -> Unit) {
    var selected by remember { mutableStateOf(-1) }
    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxHeight()
    ) {
        items(month.getDaysOfMonth(year)) {
            Text(
                text = it.toString(),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .selectable(selected = true, onClick = {
                        selected = it
                        block(it)
                    })
                    .drawBehind {
                        if (it == selected)
                            drawCircle(
                                color = Color.Magenta,
                                alpha = 0.2f,
                                radius = 40f,
                            )
                    },
                color = Color.White
            )
        }
    }
}

private fun getCurrentDate(): Triple<Int, Int, Int> {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    return with(sdf.format(Date())) {
        val date = substring(0, 2).toInt()
        val month = substring(3, 5).toInt()
        val year = substring(6, 10).toInt()

        Triple(date, month, year)
    }
}
