package com.example.krishaksathiandroid.model

data class Weather(
    val district: String,
    val season: String,
    val avgTemp: Double,
    val avgHumidity: Int,
    val avgRainfall: Int,
    val climateType: String,
    val sunlight: String,
    val drainage: String
)