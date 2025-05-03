package com.example.krishaksathiandroid

import android.graphics.Color
import android.view.LayoutInflater
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
        holder.binding.textViewCropName.text = crop.name
        holder.binding.textViewPrice.text = "Price: ₹${crop.marketPrice}"
        holder.binding.textViewDemand.text = "Demand: ${crop.demand}"
        holder.binding.textViewSupply.text = "Supply: ${crop.supply}"

        // Highlight top 3 crops
        if (crop.isTopCrop) {
            holder.binding.root.setBackgroundColor(Color.parseColor("#FFEB3B")) // Yellow highlight
        } else {
            holder.binding.root.setBackgroundColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int = cropList.size

    fun submitList(newList: List<Crop>) {
        cropList = newList
        notifyDataSetChanged()
    }
}

