package com.example.cities_mobile_dubl2.network

import com.example.cities_mobile_dubl2.model.WeatherData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("current.json")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("key") apiKey: String,
        @Query("lang") language: String
    ): Response<WeatherData>

    companion object {
        // Create a Retrofit instance with the appropriate base URL
        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create an instance of the WeatherService
        val instance: WeatherService = retrofit.create(WeatherService::class.java)
    }
}
