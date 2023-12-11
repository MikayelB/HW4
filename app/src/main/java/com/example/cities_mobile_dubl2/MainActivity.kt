package com.example.cities_mobile_dubl2

import WelcomeScreen
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cities_mobile_dubl2.constants.LOCATION_PERMISSION_REQUEST_CODE
import com.example.cities_mobile_dubl2.constants.SECOND_SCREEN_ROUTE
import com.example.cities_mobile_dubl2.constants.WELCOME_SCREEN_ROUTE
import com.example.cities_mobile_dubl2.ui.CitiesScreen
//import com.example.cities_mobile_dubl2.ui.WelcomeScreen
import com.example.cities_mobile_dubl2.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: WeatherViewModel

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        setContent {
            val navController = rememberNavController()
            AppUI(navController = navController, viewModel = viewModel, activity = this)
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.Q)
    @Composable
    fun AppUI(navController: NavHostController, viewModel: WeatherViewModel, activity: MainActivity) {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route ?: ""

        // Handle back button press
        BackHandler(onBack = {
            if (currentRoute == SECOND_SCREEN_ROUTE) {
                navController.navigate(WELCOME_SCREEN_ROUTE) {
                    popUpTo(WELCOME_SCREEN_ROUTE) {
                        inclusive = true
                    }
                }
            }
        })

        // Check and handle location permission
        activity.handleLocationPermission()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Cities Weather") }
                )
            },
            content = {
                NavHost(navController = navController, startDestination = WELCOME_SCREEN_ROUTE) {
                    composable(WELCOME_SCREEN_ROUTE) {
                        WelcomeScreen(navController = navController, activity = activity, viewModel = viewModel)
                    }
                    composable(SECOND_SCREEN_ROUTE) {
                        CitiesScreen(navController = navController, viewModel = viewModel)
                    }
                }
            }
        )
    }


    @RequiresApi(34)
    private fun handleLocationPermission() {
        if (checkLocationPermission()) {
            requestLocationUpdates()
        } else {
            requestLocationPermission()
        }
    }

    fun checkLocationPermission(): Boolean {
        return (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestLocationPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    @RequiresApi(34)
    private fun requestLocationUpdates() {
        if (checkLocationPermission()) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    viewModel.setLocation(it.latitude, it.longitude)
                }
            }
        }
    }

    @RequiresApi(34)
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocationUpdates()
                }
            }
        }
    }
}