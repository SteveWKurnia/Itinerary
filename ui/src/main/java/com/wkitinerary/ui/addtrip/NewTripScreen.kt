package com.wkitinerary.ui.addtrip

import androidx.appcompat.R.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.itinerary.R
import com.wkitinerary.ui.composables.DurationField
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTripScreen(
    modifier: Modifier = Modifier,
    viewModel: AddTripViewModel = hiltViewModel(),
    onCreateClick: (Long) -> Unit
) {
    var tripTitle by remember { mutableStateOf("") }
    var tripImage by remember { mutableStateOf(0) }
    var tripDepartureDate by remember { mutableStateOf(Date()) }
    var tripReturnDate by remember { mutableStateOf(Date()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 10.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            value = tripTitle,
            label = {
                Text(text = "Trip Title")
            },
            onValueChange = {
                tripTitle = it
            })
        DurationField({ tripDepartureDate = it }, { tripReturnDate = it })
        ImagePicker(tripImage) { selectedImageId ->
            tripImage = selectedImageId
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(3.dp),
            onClick = {
                viewModel.addTrip(tripTitle, tripImage, tripDepartureDate, tripReturnDate) {
                    onCreateClick(it)
                }
            }
        ) {
            Text(text = "Create New Trip")
        }
    }

}

@Composable
fun ImagePicker(tripImage: Int, onClick: (Int) -> Unit) {
    val image = listOf(
        R.drawable.stock_image,
        R.drawable.stock_image_2,
        R.drawable.stock_image_3,
    )
    Text(
        modifier = Modifier.padding(bottom = 8.dp),
        text = "Cover Image",
        fontSize = 16.sp,
        color = Color.White
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        content = {
            items(image) {
                PhotoItem(it, tripImage, onClick)
            }
        })
}

@Composable
fun PhotoItem(imageId: Int, tripImage: Int, onClick: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = if (imageId == tripImage) colorResource(id = R.color.purple_500) else Color.Gray
                ),
                shape = RoundedCornerShape(3.dp)
            )
            .height(100.dp)
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.clickable { onClick(imageId) })
    }
}
