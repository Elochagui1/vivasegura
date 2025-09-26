package com.example.vivasegura.ui.denuncias

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vivasegura.R
import com.example.vivasegura.databinding.ActivityDenunciasBinding
import com.google.android.material.snackbar.Snackbar

class DenunciasActivity : AppCompatActivity() {
    private lateinit var vb: ActivityDenunciasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityDenunciasBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        vb.cardFiscalia.setOnClickListener {
            openExternal("https://sicecon.fiscalia.gov.co/denuncia/LlenarFormulario")
        }
        vb.cardPolicia.setOnClickListener {
            openExternal("https://adenunciar.policia.gov.co/Adenunciar/default.aspx")
        }
    }

    private fun openExternal(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addCategory(Intent.CATEGORY_BROWSABLE)
            }
            startActivity(intent)
        } catch (_: ActivityNotFoundException) {
            Snackbar.make(vb.root, getString(R.string.cannot_open_link), Snackbar.LENGTH_LONG).show()
        }
    }
}
