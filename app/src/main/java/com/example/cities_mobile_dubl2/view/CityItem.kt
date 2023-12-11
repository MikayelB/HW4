package com.example.cities_mobile_dubl2.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cities_mobile_dubl2.model.City

@Composable
fun CityItem(city: City) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = city.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = city.description,
            style = MaterialTheme.typography.bodyMedium
        )
        Image(
            painter = painterResource(id = city.imageRes),
            contentDescription = "Image of ${city.name}",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )
    }
}