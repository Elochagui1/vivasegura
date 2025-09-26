package com.example.vivasegura.ui.premium

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.vivasegura.R
import com.example.vivasegura.databinding.ActivityPremiumBinding
import com.example.vivasegura.session.SessionManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PremiumActivity : AppCompatActivity() {
    private lateinit var vb: ActivityPremiumBinding
    private val session by lazy { SessionManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityPremiumBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Estado inicial del switch (desde DataStore)
        lifecycleScope.launch {
            val enabled = session.isPremium.first()
            vb.swPremium.isChecked = enabled
            setButtonsEnabled(enabled)
        }

        // Persistir cambios del switch
        vb.swPremium.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch { session.setPremium(isChecked) }
            setButtonsEnabled(isChecked)
        }

        // Asesoría (email)
        vb.btnCounsel.setOnClickListener {
            lifecycleScope.launch {
                val enabled = session.isPremium.first()
                if (!enabled) {
                    Snackbar.make(vb.root, getString(R.string.premium_enable_first), Snackbar.LENGTH_LONG).show()
                    return@launch
                }
                val user = session.username.first()
                val now = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
                val body = getString(R.string.premium_email_body, user.ifEmpty { "anónimo" }, now)
                openEmail(
                    to = "",
                    subject = getString(R.string.premium_email_subject),
                    body = body
                )
            }
        }

        // Chat (share sheet)
        vb.btnChat.setOnClickListener {
            lifecycleScope.launch {
                val enabled = session.isPremium.first()
                if (!enabled) {
                    Snackbar.make(vb.root, getString(R.string.premium_enable_first), Snackbar.LENGTH_LONG).show()
                    return@launch
                }
                val msg = "Hola, necesito chatear con psicología (simulación) — VivaSegura"
                shareText(msg)
            }
        }
    }

    private fun setButtonsEnabled(enabled: Boolean) {
        vb.btnCounsel.isEnabled = enabled
        vb.btnChat.isEnabled = enabled
        vb.tvNotice.alpha = if (enabled) 0.6f else 1f
    }

    private fun openEmail(to: String, subject: String, body: String) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(to)) // puedes dejar vacío en simulación
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
            }
            startActivity(intent)
        } catch (_: ActivityNotFoundException) {
            Snackbar.make(vb.root, getString(R.string.no_app_found), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun shareText(text: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            startActivity(Intent.createChooser(intent, getString(R.string.premium_share_title)))
        } catch (_: ActivityNotFoundException) {
            Snackbar.make(vb.root, getString(R.string.no_app_found), Snackbar.LENGTH_LONG).show()
        }
    }
}
