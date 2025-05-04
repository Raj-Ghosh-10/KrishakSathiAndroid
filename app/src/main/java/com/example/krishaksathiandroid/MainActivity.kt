package com.example.krishaksathiandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.krishaksathiandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Binding setup
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Toolbar setup
        setSupportActionBar(binding.toolbar)

        // ✅ Drawer Toggle
        drawerToggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.drawer_open, R.string.drawer_close
        )
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        // ✅ Navigation Menu Clicks
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            binding.drawerLayout.closeDrawers()
            when (menuItem.itemId) {
                R.id.nav_crop -> {
                    startActivity(Intent(this, CropSuggetionActivity::class.java))
                    true
                }
                // R.id.nav_weather -> {
                //     startActivity(Intent(this, WeatherActivity::class.java))
                //     true
                // }
                // R.id.nav_feature3 -> {
                //     startActivity(Intent(this, Feature3Activity::class.java))
                //     true
                // }
                else -> false
            }
        }
    }
}
