package com.example.vivasegura.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.vivasegura.core.Security
import com.example.vivasegura.data.db.DatabaseProvider
import com.example.vivasegura.data.entities.User
import com.example.vivasegura.databinding.ActivityRegisterBinding
import com.example.vivasegura.session.SessionManager
import com.example.vivasegura.ui.home.HomeActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    private lateinit var vb: ActivityRegisterBinding
    private val session by lazy { SessionManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(vb.root)
        vb.btnGoLogin.setOnClickListener {
            // Si venimos desde Login, con finish() basta para volver
            if (!isTaskRoot) {
                finish()
            } else {
                // Si Register fue abierta “sola”, garantizamos ir a Login
                startActivity(
                    Intent(this, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                )
                // No hace falta finish() por los flags
            }
        }

        val db = DatabaseProvider.get(this)
        val userDao = db.userDao()

        vb.btnCreateAccount.setOnClickListener {
            val u = vb.etNewUser.text?.toString()?.trim().orEmpty()
            val p = vb.etNewPass.text?.toString().orEmpty()

            if (u.isEmpty() || p.isEmpty()) {
                Snackbar.make(vb.root, getString(com.example.vivasegura.R.string.fill_user_pass), Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            vb.btnCreateAccount.isEnabled = false
            lifecycleScope.launch(Dispatchers.IO) {
                val exists = userDao.exists(u) > 0
                if (exists) {
                    withContext(Dispatchers.Main) {
                        vb.btnCreateAccount.isEnabled = true
                        Snackbar.make(vb.root, getString(com.example.vivasegura.R.string.user_exists), Snackbar.LENGTH_LONG).show()
                    }
                    return@launch
                }

                val hash = Security.sha256(p)
                userDao.insert(User(username = u, passwordHash = hash))

                // Auto-login
                session.saveLogin(u)

                withContext(Dispatchers.Main) {
                    startActivity(Intent(this@RegisterActivity, HomeActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
                    // No finish() necesario por los flags
                }
            }
        }
    }
}
