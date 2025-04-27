package com.example.krishaksathiandroid

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AppCompatActivity
import com.example.krishaksathiandroid.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fade-in animation setup for text
        val fadeIn = AlphaAnimation(0f, 1f).apply {
            duration = 1500 // 1.5 seconds
            fillAfter = true
        }

        // Start the fade-in animation for both TextViews
        binding.appNameTextView.startAnimation(fadeIn)
        binding.sloganTextView.startAnimation(fadeIn)

        // Move to MainActivity after 2.5 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500)
    }
}
