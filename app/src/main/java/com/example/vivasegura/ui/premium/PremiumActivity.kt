package com.example.vivasegura.ui.premium

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vivasegura.databinding.ActivityPremiumBinding

class PremiumActivity : AppCompatActivity() {
    private lateinit var vb: ActivityPremiumBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityPremiumBinding.inflate(layoutInflater)
        setContentView(vb.root)
        vb.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }
}
