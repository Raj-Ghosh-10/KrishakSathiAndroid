package com.example.krishaksathiandroid.model

data class Crop(
    val district: String,
    val season: String,
    val name: String,
    val market_price: String,
    val demand: Double,
    val supply: Double,
    val soil_type: String,
    val details: String,
    val cultivation_method: String,
    val fertilizer_recommendation: String,
    val water_needs: String,
    val harvest_time: String,
    val risk_notes: String,
    var isTopCrop: Boolean = false,
    var isExpanded: Boolean = false // 👈 NEW field
)