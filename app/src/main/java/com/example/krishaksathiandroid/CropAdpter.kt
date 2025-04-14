package com.example.krishaksathiandroid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.krishaksathiandroid.databinding.CropItemBinding
import com.example.krishaksathiandroid.model.Crop
import com.example.krishaksathiandroid.model.Weather

class CropAdapter(private val crops: List<Crop>, private val weather: Weather) :
    RecyclerView.Adapter<CropAdapter.CropViewHolder>() {

    private val expandedStates = BooleanArray(crops.size) // For handling expanded/collapsed state

    inner class CropViewHolder(val binding: CropItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CropViewHolder {
        val binding = CropItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CropViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CropViewHolder, position: Int) {
        val crop = crops[position]

        holder.binding.cropName.text = crop.name
        holder.binding.MarketRate.text = "₹${crop.rate} per quintal"
        holder.binding.demandSupply.text = "Demand: ${crop.demand}, Supply: ${crop.supply}"

        // Set content descriptions for accessibility
        holder.binding.cropName.contentDescription = "Crop Name: ${crop.name}"
        holder.binding.MarketRate.contentDescription = "Market Rate: ₹${crop.rate} per quintal"
        holder.binding.demandSupply.contentDescription = "Demand: ${crop.demand}, Supply: ${crop.supply}"

        // Highlight top 3 crops
        val context = holder.itemView.context
        val colorRes = if (crop.isTopCrop) R.color.top_crop else R.color.normal_crop
        holder.binding.cropCard.setBackgroundColor(ContextCompat.getColor(context, colorRes))

        // Show/hide extra details
        holder.binding.detailsLayout.visibility = if (crop.isExpanded) View.VISIBLE else View.GONE

        // Expanded details
        holder.binding.yieldRate.text = "Yield: ${crop.yieldPerHectare} t/ha"
        holder.binding.tempRange.text = "Temp: ${crop.tempMin}°C - ${crop.tempMax}°C"
        holder.binding.soilType.text = "Soil: ${crop.soilType}"

        // Set content descriptions for expanded details
        holder.binding.yieldRate.contentDescription = "Yield: ${crop.yieldPerHectare} tons per hectare"
        holder.binding.tempRange.contentDescription = "Temperature Range: ${crop.tempMin}°C to ${crop.tempMax}°C"
        holder.binding.soilType.contentDescription = "Soil Type: ${crop.soilType}"

        // Click to expand/collapse
        holder.binding.cropCard.setOnClickListener {
            crop.isExpanded = !crop.isExpanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = crops.size

    // Helper function to get background color for top crops
    private fun getCropBackgroundColor(position: Int, context: Context): Int {
        return if (position < 3) R.color.top_crop else R.color.normal_crop
    }
}
