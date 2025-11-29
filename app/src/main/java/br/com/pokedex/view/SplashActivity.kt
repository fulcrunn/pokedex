package br.com.pokedex.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.pokedex.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Depois de implementar o trabalho, fazer usando Coroutines e threads
        // Função responsável delay de abertura 'SplashScreen'
        Handler(Looper.getMainLooper()).postDelayed({
            // Cria a intenção de ir para a LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            // Fecha a Splash para o usuário não voltar nela com o botão "Voltar"
            finish()
        }, 3000) // 3000 ms = 3 segundos
    }
}
