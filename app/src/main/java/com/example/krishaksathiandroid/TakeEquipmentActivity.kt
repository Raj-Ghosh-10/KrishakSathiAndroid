package com.example.krishaksathiandroid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.krishaksathiandroid.databinding.ActivityTakeEquipmentBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class TakeEquipmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTakeEquipmentBinding
    private lateinit var equipmentAdapter: EquipmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakeEquipmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        equipmentAdapter = EquipmentAdapter { equipment ->
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${equipment.contactNumber}"))
            startActivity(intent)
        }
        binding.equipmentRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.equipmentRecyclerView.adapter = equipmentAdapter

        fetchEquipmentData()
    }

    private fun fetchEquipmentData() {
        val db = FirebaseFirestore.getInstance()
        db.collection("equipment_rentals")
            .whereEqualTo("available", true) // Only available equipment
            .get()
            .addOnSuccessListener { result ->
                val equipmentList = mutableListOf<Equipment>()
                for (document: QueryDocumentSnapshot in result) {
                    val equipment = document.toObject(Equipment::class.java)
                    equipmentList.add(equipment)
                }
                equipmentAdapter.submitList(equipmentList)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    this,
                    "Error fetching data: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}