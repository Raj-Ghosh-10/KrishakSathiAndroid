package com.example.krishaksathiandroid

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.krishaksathiandroid.databinding.ActivityCropSuggetionBinding
import com.example.krishaksathiandroid.model.Crop
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class CropSuggetionActivity : AppCompatActivity() {

    private lateinit var cropList: List<Crop>
    private lateinit var cropAdapter: CropAdapter
    private lateinit var binding: ActivityCropSuggetionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityCropSuggetionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load crop data from the JSON file
        loadCropData()

        // Set up Spinners (District and Season)
        val districts = cropList.map { it.district }.distinct()
        val districtAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, districts)
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDistrict.adapter = districtAdapter

        val seasons = cropList.map { it.season }.distinct()
        val seasonAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, seasons)
        seasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSeason.adapter = seasonAdapter

        // Set up RecyclerView
        binding.recyclerViewCrops.layoutManager = LinearLayoutManager(this)
        cropAdapter = CropAdapter()
        binding.recyclerViewCrops.adapter = cropAdapter

        // Set listeners for spinners
        binding.spinnerDistrict.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    updateCropList()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Do nothing
                }
            }

        binding.spinnerSeason.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                updateCropList()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        // Auto-select the first district and season for initial display
        if (districts.isNotEmpty() && seasons.isNotEmpty()) {
            binding.spinnerDistrict.setSelection(0)
            binding.spinnerSeason.setSelection(0)
            updateCropList()
        }
    }
    private fun loadCropData() {
        val inputStream =
            resources.openRawResource(R.raw.allcrop)  // ✅ Correct way to load raw file
        val reader = InputStreamReader(inputStream)
        val type = object : TypeToken<List<Crop>>() {}.type
        cropList = Gson().fromJson(reader, type)
        reader.close()  // ✅ Always good practice to close the reader
    }
    private fun updateCropList() {
        val selectedDistrict = binding.spinnerDistrict.selectedItem?.toString() ?: return
        val selectedSeason = binding.spinnerSeason.selectedItem?.toString() ?: return

        // Filter crops based on selected district and season
        val filteredCrops = cropList.filter {
            it.district == selectedDistrict && it.season == selectedSeason
        }

        // Sort crops by demand (high to low) and supply (low to high)
        val sortedCrops = filteredCrops.sortedWith(
            compareByDescending<Crop> { it.demand }
                .thenBy { it.supply }
        )

        // Highlight top 3 crops
        sortedCrops.forEachIndexed { index, crop ->
            crop.isTopCrop = index < 3  // ✅ Mark top 3 crops
        }

        // Submit the full sorted list
        cropAdapter.submitList(sortedCrops)
    }
}