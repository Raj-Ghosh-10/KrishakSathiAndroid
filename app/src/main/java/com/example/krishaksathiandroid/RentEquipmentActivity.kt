package com.example.krishaksathiandroid

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.krishaksathiandroid.databinding.ActivityRentEquipmentBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class RentEquipmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRentEquipmentBinding
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRentEquipmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Image Upload Button
        binding.uploadImageButton.setOnClickListener {
            openGalleryForImage()
        }

        // Submit Button
        binding.submitButton.setOnClickListener {
            uploadEquipmentDetails()
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
        }
    }

    private fun uploadEquipmentDetails() {
        val equipmentName = binding.equipmentName.text.toString()
        val rentPerDay = binding.rentPerDay.text.toString().toInt()
        val rentPerHour = binding.rentPerHour.text.toString().toInt()
        val ownerName = binding.userName.text.toString()
        val contactNumber = binding.contactNumber.text.toString()

        if (equipmentName.isNotEmpty() && rentPerDay > 0 && rentPerHour > 0) {
            // Upload image to Firebase Storage
            val storageRef = FirebaseStorage.getInstance().reference.child("equipment_images/${System.currentTimeMillis()}")
            imageUri?.let {
                storageRef.putFile(it).addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { imageUrl ->
                        // Save equipment data to Firestore
                        val db = FirebaseFirestore.getInstance()
                        val equipmentData = hashMapOf(
                            "name" to equipmentName,
                            "rentPerDay" to rentPerDay,
                            "rentPerHour" to rentPerHour,
                            "ownerName" to ownerName,
                            "contactNumber" to contactNumber,
                            "imageUrl" to imageUrl.toString(),
                            "available" to true
                        )

                        db.collection("equipment_rentals")
                            .add(equipmentData)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Equipment added successfully!", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error adding equipment: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }
        } else {
            Toast.makeText(this, "Please fill in all fields correctly", Toast.LENGTH_SHORT).show()
        }
    }
}