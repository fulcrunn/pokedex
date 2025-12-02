package br.com.pokedex.view

import androidx.appcompat.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.pokedex.R
import com.google.android.material.textfield.TextInputEditText
import android.widget.Button
import android.widget.Toast
import br.com.pokedex.model.Usuario
import br.com.pokedex.Sessao

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Vinculando os componentes
        val editUsuario = findViewById<TextInputEditText>(R.id.editUsuario)
        val editSenha = findViewById<TextInputEditText>(R.id.editSenha)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)

        // Ação do Botão Entrar
        btnEntrar.setOnClickListener {
            val usuarioDigitado = editUsuario.text.toString()
            val senhaDigitada = editSenha.text.toString()

            if (usuarioDigitado.isEmpty() || senhaDigitada.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                // --- CÓDIGO NOVO: Chamada Real à API ---
                val userObj = Usuario(usuarioDigitado, senhaDigitada)

                // Chama a rota /login definida no ApiService
                br.com.pokedex.network.RetrofitClient.service.login(userObj).enqueue(object : retrofit2.Callback<Map<String, String>> {
                    override fun onResponse(call: retrofit2.Call<Map<String, String>>, response: retrofit2.Response<Map<String, String>>) {
                        if (response.isSuccessful) {
                            // Login Sucesso (200 OK)
                            val resposta = response.body()
                            val nomeUsuario = resposta?.get("usuario") ?: usuarioDigitado

                            // 1. Salva na Sessão Global
                            Sessao.usuarioLogado = nomeUsuario

                            Toast.makeText(applicationContext, "Bem-vindo, $nomeUsuario!", Toast.LENGTH_SHORT).show()

                            // 2. Vai para Dashboard
                            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Erro (400, 401, etc) - Ex: Senha incorreta
                            Toast.makeText(applicationContext, "Login ou Senha incorretos", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<Map<String, String>>, t: Throwable) {
                        Toast.makeText(applicationContext, "Erro de conexão: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    } // end onCreate


// Função "Mock" que simula o backend.
private fun validarLoginMock(user: String, pass: String): Boolean {
    // Lista de usuários permitidos (Requisito: Cadastre previamente 3 usuários)
    val listaUsuarios = listOf(
        Usuario("admin", "1234"),
        Usuario("ash", "pikachu"),
        Usuario("misty", "togepi")
    )

    // Verifica se existe alguém na lista com esse usuário e essa senha
    // O comando 'any' retorna true se encontrar pelo menos um correspondente
    return listaUsuarios.any { it.usuario == user && it.senha == pass }
}

// Função para mostrar o Alerta
private fun mostrarErroLogin() {
    AlertDialog.Builder(this)
        .setTitle("Opss..houve um erro.")
        .setMessage("Login ou Senha incorretos")
        .setPositiveButton("OK", null)
        .show()
}
} // end LoginActivity
