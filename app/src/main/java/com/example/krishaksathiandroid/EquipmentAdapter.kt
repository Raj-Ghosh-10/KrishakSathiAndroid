package com.example.krishaksathiandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.krishaksathiandroid.databinding.ItemEquipmentBinding

data class Equipment(
    val name: String = "",
    val rentPerDay: Int = 0,
    val rentPerHour: Int = 0,
    val ownerName: String = "",
    val contactNumber: String = "",
    val imageUrl: String = "",
    val available: Boolean = true
)

class EquipmentAdapter(private val onItemClick: (Equipment) -> Unit) :
    ListAdapter<Equipment, EquipmentAdapter.EquipmentViewHolder>(EquipmentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {
        val binding =
            ItemEquipmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EquipmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
        val equipment = getItem(position)
        holder.bind(equipment)
    }

    inner class EquipmentViewHolder(private val binding: ItemEquipmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(equipment: Equipment) {
            binding.equipmentName.text = equipment.name
            binding.rentPerDay.text = "Rent per day: ${equipment.rentPerDay}₹"
            binding.rentPerHour.text = "Rent per hour: ${equipment.rentPerHour}₹"
            binding.ownerName.text = "Owner: ${equipment.ownerName}"

            binding.root.setOnClickListener {
                onItemClick(equipment)
            }
        }
    }

    class EquipmentDiffCallback : DiffUtil.ItemCallback<Equipment>() {
        override fun areItemsTheSame(oldItem: Equipment, newItem: Equipment): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Equipment, newItem: Equipment): Boolean {
            return oldItem == newItem
        }
    }
}
