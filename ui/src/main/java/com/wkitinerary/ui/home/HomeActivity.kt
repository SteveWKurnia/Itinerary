package com.wkitinerary.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wkitinerary.ui.addtrip.AddTripActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val uiState = viewModel.uiState.collectAsState()
            val context = LocalContext.current

            LaunchedEffect(Unit) {
                viewModel.getTrip()
            }

            when (uiState.value) {
                HomeViewModel.UiState.Empty -> AddTripButton(onClick = {
                    val intent = Intent(context, AddTripActivity::class.java)
                    context.startActivity(intent)
                })
                is HomeViewModel.UiState.Success -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        items((uiState.value as HomeViewModel.UiState.Success).trips) { homeItem ->
                            when (homeItem) {
                                HomeItems.AddTrip -> AddTripButton(onClick = {
                                    val intent = Intent(context, AddTripActivity::class.java)
                                    context.startActivity(intent)
                                })
                                is HomeItems.Trip -> TripItem(
                                    title = homeItem.title,
                                    imageResource = homeItem.image,
                                    departureDate = homeItem.departureDate,
                                    returnDate = homeItem.returnDate
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TripItem(title: String, imageResource: Int, departureDate: String, returnDate: String) {
    ItemContainer(
        modifier = Modifier.border(
            width = 1.dp,
            shape = RoundedCornerShape(3.dp),
            color = Color.White
        )
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Stock Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    modifier = Modifier.wrapContentSize(),
                    textAlign = TextAlign.Center
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = departureDate,
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.wrapContentSize(),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = returnDate,
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.wrapContentSize(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun AddTripButton(onClick: suspend () -> Unit) {
    val stroke = Stroke(
        width = 5f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )

    val coroutineScope = CoroutineScope(Dispatchers.IO)
    ItemContainer(modifier = Modifier
        .drawBehind {
            drawRoundRect(
                color = Color.White,
                style = stroke
            )
        }
        .clickable {
            coroutineScope.launch {
                onClick()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add image",
                modifier = Modifier.wrapContentSize(),
                tint = Color.White,
            )
            Text(text = "Plan a new trip!", color = Color.White)
        }
    }
}

@Composable
fun ItemContainer(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .height(100.dp)
            .fillMaxWidth()
            .then(modifier)
    ) { content() }
}
