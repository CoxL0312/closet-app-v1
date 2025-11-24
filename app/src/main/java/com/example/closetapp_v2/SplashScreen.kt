package com.example.closetapp_v2

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen_v1)
        var image_logo_view = findViewById(R.id.splash_screen_logo_imageView) as ImageView
        image_logo_view.alpha = 0f
        image_logo_view.animate().setDuration(3000).alpha(1f).withEndAction {
            val landing_page = Intent(this, MainActivity::class.java)
            startActivity(landing_page)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splash_screen_logo_imageView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}