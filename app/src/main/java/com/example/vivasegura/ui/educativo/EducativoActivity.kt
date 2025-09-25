package com.example.vivasegura.ui.educativo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vivasegura.databinding.ActivityEducativoBinding

class EducativoActivity : AppCompatActivity() {
    private lateinit var vb: ActivityEducativoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityEducativoBinding.inflate(layoutInflater)
        setContentView(vb.root)
        vb.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }
}
