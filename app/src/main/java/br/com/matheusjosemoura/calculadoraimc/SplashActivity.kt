package br.com.matheusjosemoura.calculadoraimc

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val intentLogin = Intent(this, LoginActivity::class.java)

            startActivity(intentLogin)
            finish()
        }, 3000)
    }
}