package com.example.krishaksathiandroid.model

data class Crop(
    val district: String,
    val season: String,
    val name: String,
    val marketPrice: String,
    val demand: Double,
    val supply: Double,
    val soilType: String,
    val details: String,
    val cultivationMethod: String,
    val fertilizerRecommendation: String,
    val waterNeeds: String,
    val harvestTime: String,
    val riskNotes: String,
    var isTopCrop: Boolean = false
)
