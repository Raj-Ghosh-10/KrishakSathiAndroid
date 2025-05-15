package com.example.krishaksathiandroid

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.krishaksathiandroid.databinding.ActivityRentEquipmentBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class RentEquipmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRentEquipmentBinding
    private var imageUri: Uri? = null

    // New Activity Result API
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
            Toast.makeText(this, "Image Selected!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

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
        pickImageLauncher.launch("image/*")
    }

    private fun uploadEquipmentDetails() {
        val equipmentName = binding.equipmentName.text.toString()
        val rentPerDay = binding.rentPerDay.text.toString()
        val rentPerHour = binding.rentPerHour.text.toString()
        val ownerName = binding.userName.text.toString()
        val contactNumber = binding.contactNumber.text.toString()

        // Basic validation
        if (equipmentName.isEmpty() || rentPerDay.isEmpty() || rentPerHour.isEmpty() ||
            ownerName.isEmpty() || contactNumber.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show()
            return
        }

        val rentPerDayInt = rentPerDay.toIntOrNull()
        val rentPerHourInt = rentPerHour.toIntOrNull()

        if (rentPerDayInt == null || rentPerHourInt == null || rentPerDayInt <= 0 || rentPerHourInt <= 0) {
            Toast.makeText(this, "Enter valid rent values", Toast.LENGTH_SHORT).show()
            return
        }

        // Upload image to Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference
            .child("equipment_images/${System.currentTimeMillis()}.jpg")

        imageUri?.let { uri ->
            storageRef.putFile(uri).addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { imageUrl ->
                    // Save equipment data to Firestore
                    val equipmentData = hashMapOf(
                        "name" to equipmentName,
                        "rentPerDay" to rentPerDayInt,
                        "rentPerHour" to rentPerHourInt,
                        "ownerName" to ownerName,
                        "contactNumber" to contactNumber,
                        "imageUrl" to imageUrl.toString(),
                        "available" to true
                    )

                    FirebaseFirestore.getInstance()
                        .collection("equipment_rentals")
                        .add(equipmentData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Equipment added successfully!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Image upload failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
