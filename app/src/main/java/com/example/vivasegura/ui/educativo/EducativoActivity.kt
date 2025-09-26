package com.example.vivasegura.ui.educativo

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.vivasegura.R
import com.example.vivasegura.databinding.ActivityEducativoBinding
import com.example.vivasegura.databinding.ItemLinkBinding
import com.google.android.material.snackbar.Snackbar

class EducativoActivity : AppCompatActivity() {
    private lateinit var vb: ActivityEducativoBinding

    data class Article(val title: String, val url: String)

    // 👇 Cambia, quita o agrega los links que quieras
    private val links = listOf(
        Article("CÓMO DENUNCIAR DE FORMA SEGURA", "https://www.fiscalia.gov.co/colombia/atencion-al-ciudadano/como-denunciar/"),
        Article("LÍNEAS DE EMERGENCIA – 123", "https://www.policia.gov.co/linea-123"),
        Article("GUÍA DE ATENCIÓN A VÍCTIMAS", "https://www.unidadvictimas.gov.co/es")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityEducativoBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Inflar tarjetas de links
        val container = vb.containerLinks
        val inflater = LayoutInflater.from(this)

        links.forEach { art ->
            val item = ItemLinkBinding.inflate(inflater, container, false)
            item.tvTitle.text = art.title
            // Muestra dominio bonito si existe
            val host = runCatching { Uri.parse(art.url).host ?: art.url }.getOrDefault(art.url)
            item.tvUrl.text = host.uppercase()
            item.root.setOnClickListener { openExternal(art.url) }
            container.addView(item.root)
        }
    }

    private fun openExternal(url: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addCategory(Intent.CATEGORY_BROWSABLE)
            })
        } catch (_: ActivityNotFoundException) {
            Snackbar.make(vb.root, getString(R.string.cannot_open_link), Snackbar.LENGTH_LONG).show()
        }
    }
}
