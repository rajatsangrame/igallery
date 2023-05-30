package com.example.igallery.ui.splash

import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.igallery.R
import com.example.igallery.ui.main.MainActivity

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            MainActivity.start(this)
            finish()
        }, 2000)
    }
}
