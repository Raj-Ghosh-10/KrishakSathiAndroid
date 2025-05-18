package com.example.krishaksathiandroid

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.krishaksathiandroid.databinding.CropItemBinding
import com.example.krishaksathiandroid.model.Crop

class CropAdapter : RecyclerView.Adapter<CropAdapter.CropViewHolder>() {

    private var cropList: List<Crop> = emptyList()

    inner class CropViewHolder(val binding: CropItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CropViewHolder {
        val binding = CropItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CropViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CropViewHolder, position: Int) {
        val crop = cropList[position]
        holder.binding.textViewCropName.text = "ফসলের নাম:- ${crop.name}"
        holder.binding.textViewPrice.text = "বাজার মূল্য:- ${crop.market_price}"
        holder.binding.textViewDemand.text = "চাহিদা:- ${crop.demand}"
        holder.binding.textViewSupply.text = "জোগান:- ${crop.supply}"

        // Set extra details

        holder.binding.textViewDetails.text = "বিস্তারিত:- ${crop.details}"

        holder.binding.textViewFertilizerRecommendation.text =
            "সার পরামর্শ:- ${crop.fertilizer_recommendation}"

        holder.binding.textViewSoilType.text = "মাটির ধরন:- ${crop.soil_type}"

        holder.binding.textViewCultivationMethod.text = "চাষের পদ্ধতি:- ${crop.cultivation_method}"

        holder.binding.textViewWaterNeeds.text = "সেচের প্রয়োজন:- ${crop.water_needs}"

        holder.binding.textViewHarvestTime.text = "ফসল কাটার সময়:- ${crop.harvest_time}"

        holder.binding.textViewRiskNotes.text = "সতর্কতা:- ${crop.risk_notes}"

        // Show/hide extra details based on isExpanded
        holder.binding.layoutExtraDetails.visibility =
            if (crop.isExpanded) View.VISIBLE else View.GONE

        // Highlight top 3 crops
        if (crop.isTopCrop) {
            holder.binding.root.setBackgroundColor(Color.parseColor("#ffff66")) // Yellow highlight
        } else {
            holder.binding.root.setBackgroundColor(Color.parseColor("#aaff80"))
        }

        // Handle click to expand/collapse
        holder.binding.root.setOnClickListener {
            crop.isExpanded = !crop.isExpanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = cropList.size

    fun submitList(newList: List<Crop>) {
        cropList = newList
        notifyDataSetChanged()
    }
}