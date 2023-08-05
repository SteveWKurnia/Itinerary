package com.wkitinerary.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.wkitinerary.ui.addtrip.NewTripScreen
import com.wkitinerary.ui.home.HomeScreen
import com.wkitinerary.ui.tripdetail.TripDetailScreen
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContainer()
        }
    }
}

@Composable
fun MainContainer() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onCreateClick = { navController.navigate("new_trip") },
                onTripClick = { navController.navigate("trip_detail/$it") }
            )
        }

        composable("new_trip") {
            NewTripScreen {
                navController.navigate("trip_detail/$it") {
                    popUpTo("home")
                }
            }
        }

        composable(
            "trip_detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) {
            val id = it.arguments?.getLong("id")
            TripDetailScreen(id = id)
        }
    }
}
