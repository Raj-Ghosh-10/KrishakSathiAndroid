package com.example.krishaksathiandroid.model

data class Crop(
    val name: String,
    val englishName: String,
    val season: String,
    val soilType: String,
    val tempMin: Double,
    val tempMax: Double,
    val humidityMin: Double,
    val humidityMax: Double,
    val climate: String,
    val drainage: String,
    val sunlight: String,
    val demand: Int,
    val supply: Int,
    val rate: Int,
    val category: String,
    val durationDays: Int,
    val diseaseResistance: String,
    val fertilizerNeed: String,
    val yieldPerHectare: Double,
    val irrigationNeeded: String,
    val fertilizerType: String,
    val sowingMonths: String,
    val harvestingMonths: String,
    var isExpanded: Boolean = false,
    var isTopCrop: Boolean = false // This will be used to highlight the top 3 crops
)
