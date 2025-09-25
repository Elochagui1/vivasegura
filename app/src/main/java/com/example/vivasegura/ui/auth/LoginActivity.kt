package com.example.vivasegura.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.vivasegura.data.db.DatabaseProvider
import com.example.vivasegura.data.repo.AuthRepository
import com.example.vivasegura.databinding.ActivityLoginBinding
import com.example.vivasegura.session.SessionManager
import com.example.vivasegura.ui.home.HomeActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var vb: ActivityLoginBinding
    private lateinit var repo: AuthRepository
    private val session by lazy { SessionManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(vb.root)
        vb.btnGoRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // ⚠️ Obtén la DB de forma segura con applicationContext
        val db = DatabaseProvider.get(this)
        repo = AuthRepository(db.userDao())

        vb.btnLogin.setOnClickListener {
            val user = vb.etUser.text?.toString()?.trim().orEmpty()
            val pass = vb.etPass.text?.toString().orEmpty()
            if (user.isEmpty() || pass.isEmpty()) {
                Snackbar.make(vb.root, "Completa usuario y clave", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (user.isEmpty() || pass.isEmpty()) {
            Snackbar.make(vb.root, getString(com.example.vivasegura.R.string.fill_user_pass), Snackbar.LENGTH_LONG).show()
            return@setOnClickListener
             }

            vb.btnLogin.isEnabled = false
            lifecycleScope.launch(Dispatchers.IO) {
                val result = repo.login(user, pass)
                withContext(Dispatchers.Main) {
                    vb.btnLogin.isEnabled = true
                    result.onSuccess {
                        lifecycleScope.launch(Dispatchers.IO) { session.saveLogin(user) }
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                    }.onFailure {
                        Snackbar.make(vb.root, it.message ?: "Error", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
