package com.example.cities_mobile_dubl2.ui

import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.cities_mobile_dubl2.model.WeatherData
import com.example.cities_mobile_dubl2.viewmodel.WeatherViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(navController: NavController, activity: MainActivity, viewModel: WeatherViewModel) {
    val context = LocalContext.current

    var weatherData by remember { mutableStateOf<List<WeatherData>?>(null) }
    var isDataLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(weatherData) {
        if (weatherData != null && weatherData!!.isNotEmpty() && weatherData!![0].location.name.isNotEmpty()) {
            isDataLoaded = true
        }
    }

    viewModel.weatherData.observeAsState().value?.let {
        weatherData = it
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Button(
            onClick = {
                navController.navigateUp()
            },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.Start),

            ) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")        }
    }
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
            if (isDataLoaded) {
                val cityName = weatherData?.get(0)?.location?.name ?: ""
                Text(
                    text = "Current Temperature in $cityName: ${weatherData?.get(0)?.current?.temp_c}°C",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            } else {
                Text(
                    text = "Loading...",
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
