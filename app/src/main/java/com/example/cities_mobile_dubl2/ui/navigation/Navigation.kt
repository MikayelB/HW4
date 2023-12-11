package com.example.cities_mobile_dubl2.ui.navigation
//
//import androidx.navigation.navArgument
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavController
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.NavType
//import androidx.navigation.compose.*
//import com.example.cities_mobile_dubl2.ui.CitiesScreen
//import com.example.cities_mobile_dubl2.ui.CityDetailScreen
//import com.example.cities_mobile_dubl2.ui.WelcomeScreen
//
//@Composable
//fun Navigation() {
//    val navController = rememberNavController()
//
//    NavHost(
//        navController = navController,
//        startDestination = "welcome_screen"
//    ) {
//        addWelcomeScreen(navController)
//        addCitiesScreen(navController)
//        addCityDetailScreen(navController)
//    }
//}
//
//private fun NavGraphBuilder.addWelcomeScreen(navController: NavController) {
//    composable("welcome_screen") {
//        WelcomeScreen(navController)
//    }
//}
//
//private fun NavGraphBuilder.addCitiesScreen(navController: NavController) {
//    composable("cities_screen") {
//        CitiesScreen(navController)
//    }
//}
//
//private fun NavGraphBuilder.addCityDetailScreen(navController: NavController) {
//    composable(
//        route = "city_detail_screen/{cityId}",
//        arguments = listOf(navArgument("cityId") { type = NavType.StringType })
//    ) { entry ->
//        val cityId = entry.arguments?.getString("cityId") ?: ""
//        CityDetailScreen(navController, cityId)
//    }
//}
