package com.example.vivasegura.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.vivasegura.databinding.ActivityHomeBinding
import com.example.vivasegura.session.SessionManager
import com.example.vivasegura.ui.auth.LoginActivity
import com.example.vivasegura.ui.denuncias.DenunciasActivity
import com.example.vivasegura.ui.educativo.EducativoActivity
import com.example.vivasegura.ui.panico.PanicoActivity
import com.example.vivasegura.ui.premium.PremiumActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {
    private lateinit var vb: ActivityHomeBinding
    private val session by lazy { SessionManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(vb.root)

        // Navegación del menú
        vb.cardPremium.setOnClickListener {
            startActivity(Intent(this, PremiumActivity::class.java))
        }
        vb.cardPanico.setOnClickListener {
            startActivity(Intent(this, PanicoActivity::class.java))
        }
        vb.cardEducativo.setOnClickListener {
            startActivity(Intent(this, EducativoActivity::class.java))
        }
        vb.cardDenuncias.setOnClickListener {
            startActivity(Intent(this, DenunciasActivity::class.java))
        }

        // Cerrar sesión
        vb.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setMessage(com.example.vivasegura.R.string.logout_confirm)
                .setNegativeButton(com.example.vivasegura.R.string.cancel, null)
                .setPositiveButton(com.example.vivasegura.R.string.accept) { _, _ ->
                    doLogout()
                }
                .show()
        }
    }

    private fun doLogout() {
        vb.btnLogout.isEnabled = false
        lifecycleScope.launch {
            withContext(Dispatchers.IO) { session.logout() }
            val intent = Intent(this@HomeActivity, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
    }
}
