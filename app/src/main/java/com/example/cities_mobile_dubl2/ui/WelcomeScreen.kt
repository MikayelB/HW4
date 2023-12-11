import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.cities_mobile_dubl2.MainActivity
import com.example.cities_mobile_dubl2.R
import com.example.cities_mobile_dubl2.constants.SECOND_SCREEN_ROUTE
import com.example.cities_mobile_dubl2.model.WeatherData
import com.example.cities_mobile_dubl2.viewmodel.WeatherViewModel


@Composable
fun WelcomeScreen(navController: NavController, activity: MainActivity, viewModel: WeatherViewModel) {
    val context = LocalContext.current

    var weatherData by remember { mutableStateOf<List<WeatherData>?>(null) }
    var isDataLoaded by remember { mutableStateOf(false) }

    var showSettingsDialog by remember { mutableStateOf(false) }
    var isCelsiusSelected by remember { mutableStateOf(true) }

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
        IconButton(
            onClick = {
                showSettingsDialog = true
            },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.Start),

            ) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
        }
    }

    if (showSettingsDialog) {
        SettingsDialog(
            onDismiss = {
                showSettingsDialog = false
            },
            onCelsiusSelected = {
                isCelsiusSelected = it
            },
            isCelsiusSelected = isCelsiusSelected
        )
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
                val temperatureUnit = if (isCelsiusSelected) "°C" else "°F"
                Text(
                    text = "Current Temperature in $cityName: ${weatherData?.get(0)?.current?.temp_c}$temperatureUnit",
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

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    onCelsiusSelected: (Boolean) -> Unit,
    isCelsiusSelected: Boolean
) {
    var selectedOption by remember { mutableStateOf(if (isCelsiusSelected) "Celsius" else "Fahrenheit") }

    Dialog(
        onDismissRequest = { onDismiss() },
        content = {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background) // Set the background color
                    .padding(.09.dp)
                    .border(1.dp, Color.Black)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Choose Temperature Unit",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TemperatureUnitOption(
                            "Celsius",
                            selectedOption == "Celsius"
                        ) {
                            selectedOption = "Celsius"
                            onCelsiusSelected(true)
                        }
                        TemperatureUnitOption(
                            "Fahrenheit",
                            selectedOption == "Fahrenheit"
                        ) {
                            selectedOption = "Fahrenheit"
                            onCelsiusSelected(false)
                        }
                    }
                }
            }
        }
    )

}

@Composable
fun TemperatureUnitOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.background
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(.5f)
            .fillMaxHeight((.1f))
            .clickable {
                onClick()
            }
            .background(color = Color.White),
        shape = MaterialTheme.shapes.medium,
        border =  if (isSelected) BorderStroke(1.dp, Color.Black) else BorderStroke(1.dp, Color.Gray),

    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(16.dp),
                color = if (isSelected) Color.Black else Color.Gray,
            )
        }
    }
}




