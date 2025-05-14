package com.example.krishaksathiandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.krishaksathiandroid.databinding.ActivityEquipmentOptionsBinding

class EquipmentOptionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEquipmentOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEquipmentOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Rent Equipment Button
        binding.rentEquipmentButton.setOnClickListener {
            val intent = Intent(this, RentEquipmentActivity::class.java)
            startActivity(intent)
        }

        // Take Equipment Button
        binding.takeEquipmentButton.setOnClickListener {
            val intent = Intent(this, TakeEquipmentActivity::class.java)
            startActivity(intent)
        }

        // Admin Panel Button
        binding.adminPanelButton.setOnClickListener {
            val intent = Intent(this, AdminPanelActivity::class.java)
            startActivity(intent)
        }
    }
}