package com.example.cities_mobile_dubl2.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cities_mobile_dubl2.data.cities
import com.example.cities_mobile_dubl2.model.City
import com.example.cities_mobile_dubl2.model.WeatherData

@Composable
fun WeatherList(weatherList: List<WeatherData>) {
    LazyColumn {
        itemsIndexed(weatherList) { index, weather ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                CityItem(city = City(name = cities[index].name, description = cities[index].description, imageRes = cities[index].imageRes))
                Text(
                    text = "Temperature: ${weather.current.temp_c}°C",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
