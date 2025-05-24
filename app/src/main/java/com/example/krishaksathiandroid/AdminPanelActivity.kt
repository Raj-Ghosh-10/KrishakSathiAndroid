package com.example.krishaksathiandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.krishaksathiandroid.databinding.ActivityAdminPanelBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class AdminPanelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminPanelBinding
    private lateinit var equipmentAdapter: EquipmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        equipmentAdapter = EquipmentAdapter { equipment ->
            // Admin can delete equipment or edit it
            deleteEquipment(equipment)
        }

        binding.equipmentRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.equipmentRecyclerView.adapter = equipmentAdapter

        fetchEquipmentData()

        binding.addEquipmentButton.setOnClickListener {
            // Open Add Equipment screen
            val intent = Intent(this, AddEquipmentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchEquipmentData() {
        val db = FirebaseFirestore.getInstance()
        db.collection("equipment_rentals")
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

    private fun deleteEquipment(equipment: Equipment) {
        val db = FirebaseFirestore.getInstance()
        db.collection("equipment_rentals")
            .document(equipment.name)  // Assuming name is unique
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Equipment deleted successfully", Toast.LENGTH_SHORT).show()
                fetchEquipmentData() // Refresh the list
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    this,
                    "Error deleting equipment: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}