package com.example.vivasegura.ui.denuncias

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vivasegura.databinding.ActivityDenunciasBinding

class DenunciasActivity : AppCompatActivity() {
    private lateinit var vb: ActivityDenunciasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityDenunciasBinding.inflate(layoutInflater)
        setContentView(vb.root)
        vb.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }
}
