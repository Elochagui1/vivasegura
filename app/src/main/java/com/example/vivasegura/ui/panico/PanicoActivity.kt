package com.example.vivasegura.ui.panico

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vivasegura.databinding.ActivityPanicoBinding

class PanicoActivity : AppCompatActivity() {
    private lateinit var vb: ActivityPanicoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityPanicoBinding.inflate(layoutInflater)
        setContentView(vb.root)
        vb.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }
}
