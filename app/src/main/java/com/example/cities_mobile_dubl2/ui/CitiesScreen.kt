package com.example.cities_mobile_dubl2.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cities_mobile_dubl2.view.WeatherList
import com.example.cities_mobile_dubl2.viewmodel.WeatherViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CitiesScreen(navController: NavHostController, viewModel: WeatherViewModel = viewModel()) {
    val weatherList = viewModel.weatherData.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(35.dp))
        Button(
            onClick = {
                navController.navigateUp()
            },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.Start),

            ) {
            Text(text = "Go Back")
        }


        if (weatherList != null) {
            WeatherList(weatherList = weatherList)
        }

    }
}
