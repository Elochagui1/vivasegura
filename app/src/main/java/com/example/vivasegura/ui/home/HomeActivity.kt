package com.example.vivasegura.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vivasegura.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var vb: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(vb.root)
    }
}
