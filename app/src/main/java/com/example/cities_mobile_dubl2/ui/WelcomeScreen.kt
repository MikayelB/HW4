package com.example.cities_mobile_dubl2.ui

import android.content.Context
import android.location.LocationManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.cities_mobile_dubl2.MainActivity
import com.example.cities_mobile_dubl2.R
import com.example.cities_mobile_dubl2.constants.SECOND_SCREEN_ROUTE
import com.example.cities_mobile_dubl2.network.WeatherService
import com.example.cities_mobile_dubl2.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch
import android.location.LocationListener
import kotlinx.coroutines.delay


@Composable
fun WelcomeScreen(navController: NavController, activity: MainActivity, viewModel: WeatherViewModel) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.welcome_message),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        if (activity.checkLocationPermission()) {
            val weatherData = viewModel.weatherData.value
            if (weatherData != null) {
                Text(
                    text = "Current Temperature: ${weatherData[0].current.temp_c}°C",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            } else {
                Text(
                    text = "No weather available",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        } else {
            Text(
                text = "No location available",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(SECOND_SCREEN_ROUTE)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = stringResource(id = R.string.explore_cities))
        }
    }
}

private fun fetchWeatherForCurrentLocation(activity: MainActivity, viewModel: WeatherViewModel) {
    val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    val locationListener = LocationListener { location ->
        val latitude = location.latitude
        val longitude = location.longitude

        viewModel.setLocation(latitude, longitude)
        activity.lifecycleScope.launch {
            delay(5000)
            viewModel.fetchWeather("current_location")
        }
    }

    try {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0, 0f,
            locationListener
        )
    } catch (e: SecurityException) {
        e.printStackTrace()
    }
}