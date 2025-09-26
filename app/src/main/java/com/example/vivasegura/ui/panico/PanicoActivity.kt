package com.example.vivasegura.ui.panico

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.vivasegura.R
import com.example.vivasegura.databinding.ActivityPanicoBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PanicoActivity : AppCompatActivity() {
    private lateinit var vb: ActivityPanicoBinding
    private var pulse: ObjectAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityPanicoBinding.inflate(layoutInflater)
        setContentView(vb.root)

        // Vibra breve al entrar
        vibrateOnce(250)

        // Pulso infinito sobre la sirena 🚨
        startPulse()

        // Simulación: “enviando” por ~4.5s y luego “enviado”
        lifecycleScope.launch {
            delay(4500)
            onSent()
        }

        // Tap largo para salir en cualquier momento (atajo opcional)
        vb.rootAlert.setOnLongClickListener {
            finish()
            true
        }
    }

    private fun onSent() {
        // Detén el pulso y cambia feedback visual
        stopPulse()
        vb.progress.visibility = android.view.View.GONE
        vb.tvSubtitle.text = getString(R.string.panic_subtitle_sent)
        // Cambia el emoji a un “check” breve
        vb.ivSiren.text = "✅"
        Snackbar.make(vb.rootAlert, getString(R.string.panic_sent_toast), Snackbar.LENGTH_LONG).show()

        // Auto-salida suave tras 1.5s (opcional)
        lifecycleScope.launch {
            delay(1500)
            finish()
        }
    }

    private fun startPulse() {
        val scaleX = PropertyValuesHolder.ofFloat(android.view.View.SCALE_X, 1f, 1.08f, 1f)
        val scaleY = PropertyValuesHolder.ofFloat(android.view.View.SCALE_Y, 1f, 1.08f, 1f)
        pulse = ObjectAnimator.ofPropertyValuesHolder(vb.ivSiren, scaleX, scaleY).apply {
            duration = 700
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.RESTART
            start()
        }
    }

    private fun stopPulse() {
        pulse?.cancel()
        pulse = null
        vb.ivSiren.scaleX = 1f
        vb.ivSiren.scaleY = 1f
    }

    private fun vibrateOnce(ms: Long) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vm = getSystemService(VibratorManager::class.java)
                vm?.defaultVibrator?.vibrate(
                    VibrationEffect.createOneShot(ms, VibrationEffect.DEFAULT_AMPLITUDE)
                )
            } else {
                @Suppress("DEPRECATION")
                val v = getSystemService(VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(ms, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION") v.vibrate(ms)
                }
            }
        } catch (_: Exception) { /* no bloquear */ }
    }

    override fun onDestroy() {
        stopPulse()
        super.onDestroy()
    }
}
