package com.example.krishaksathiandroid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.krishaksathiandroid.databinding.ActivityAddEquipmentBinding
import com.google.firebase.firestore.FirebaseFirestore

class AddEquipmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEquipmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEquipmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitButton.setOnClickListener {
            val name = binding.equipmentName.text.toString()
            val rentPerDay = binding.rentPerDay.text.toString().toInt()
            val rentPerHour = binding.rentPerHour.text.toString().toInt()
            val ownerName = binding.ownerName.text.toString()
            val contactNumber = binding.contactNumber.text.toString()
            val imageUrl = binding.uploadImageButton.text.toString()

            val equipment =
                Equipment(name, rentPerDay, rentPerHour, ownerName, contactNumber, imageUrl)

            val db = FirebaseFirestore.getInstance()
            db.collection("equipment_rentals")
                .document(name)  // Use name as unique ID
                .set(equipment)
                .addOnSuccessListener {
                    Toast.makeText(this, "Equipment added successfully", Toast.LENGTH_SHORT).show()
                    finish() // Go back to Admin Panel
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        this,
                        "Error adding equipment: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}