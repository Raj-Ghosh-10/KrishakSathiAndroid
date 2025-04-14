package com.example.krishaksathiandroid

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.krishaksathiandroid.databinding.ActivityMainBinding
import com.example.krishaksathiandroid.model.Crop
import com.example.krishaksathiandroid.model.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cropAdapter: CropAdapter
    private var allCrops: List<Crop> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load crop data
        allCrops = loadCropData()

        // Load weather data for the specific district and season
        val weather = loadWeatherData("Kolkata", "Rabi") // Initially load for a static district/season

        // Set up the RecyclerView with an empty adapter initially
        cropAdapter = CropAdapter(emptyList(), weather)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = cropAdapter

        // Set up the Spinners with predefined values
        setupSpinners()

        // Filter button logic
        binding.filterButton.setOnClickListener {
            val selectedDistrict = binding.districtSpinner.selectedItem.toString()
            val selectedSeason = binding.seasonSpinner.selectedItem.toString()
            val selectedSoilType = binding.soilTypeSpinner.selectedItem.toString()

            // Filter crops based on the selections
            val filteredCrops = filterCrops(selectedDistrict, selectedSeason, selectedSoilType)

            // Update the RecyclerView with the filtered crops
            cropAdapter = CropAdapter(filteredCrops, weather)
            binding.recyclerView.adapter = cropAdapter
        }
    }

    private fun loadCropData(): List<Crop> {
        val inputStream = resources.openRawResource(R.raw.crop)
        val reader = InputStreamReader(inputStream)
        val type = object : TypeToken<List<Crop>>() {}.type
        return Gson().fromJson(reader, type)
    }

    private fun loadWeatherData(district: String, season: String): Weather {
        try {
            val inputStream = resources.openRawResource(R.raw.weather) // Use R.raw.<filename> without the extension
            val reader = InputStreamReader(inputStream)
            val weatherData = Gson().fromJson(reader, Array<Weather>::class.java)
            return weatherData.first { weather -> weather.district == district && weather.season == season }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    private fun setupSpinners() {
        // District Spinner setup
        val districts = listOf(
            "Kolkata", "Hooghly", "Howrah", "North 24 Parganas", "South 24 Parganas",
            "Purba Medinipur", "Paschim Medinipur", "Burdwan", "Malda", "Murshidabad",
            "Jalpaiguri", "Dakshin Dinajpur", "Uttar Dinajpur", "Nadia", "Cooch Behar"
        )
        val districtAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, districts)
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.districtSpinner.adapter = districtAdapter

        // Season Spinner setup
        val seasons = listOf("Rabi", "Kharif", "Zaid")
        val seasonAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, seasons)
        seasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.seasonSpinner.adapter = seasonAdapter

        // Soil Type Spinner setup
        val soilTypes = listOf("Sandy", "Loamy", "Clay", "Sandy-Loam", "Clay-Loam", "Alluvial", "Red Soil", "Black Soil")
        val soilTypeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, soilTypes)
        soilTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.soilTypeSpinner.adapter = soilTypeAdapter
    }

    private fun filterCrops(district: String, season: String, soilType: String): List<Crop> {
        val weather = loadWeatherData(district, season)
        // Filter crops based on district, season, soil type, and weather matching
        val filteredCrops = allCrops.filter { crop ->
            // Match the weather requirements
            val matchesWeather = crop.tempMin <= weather.avgTemp && crop.tempMax >= weather.avgTemp
            val matchesDistrict = crop.season == season
            val matchesSoilType = crop.soilType == soilType || soilType == "All"

            matchesDistrict && matchesSoilType && matchesWeather
        }

        // Sort the crops based on demand (high demand, low supply)
        val sortedCrops = filteredCrops.sortedByDescending { it.demand - it.supply }

        // Get the top 3 crops based on demand and supply (demand > supply)
        val top3Crops = sortedCrops.take(3)

        // Highlight the top 3 crops
        sortedCrops.forEachIndexed { index, crop ->
            crop.isTopCrop = index < 3
        }

        return sortedCrops
    }
}
