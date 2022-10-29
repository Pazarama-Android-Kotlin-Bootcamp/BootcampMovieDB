package com.merttoptas.botcaampmoviedb.feature.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.merttoptas.botcaampmoviedb.MainActivity
import com.merttoptas.botcaampmoviedb.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        navigateToMain()
    }

    private fun navigateToMain() {
        lifecycleScope.launch {
            delay(2000)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}