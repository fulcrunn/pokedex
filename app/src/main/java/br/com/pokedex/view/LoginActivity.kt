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

            // Validação simples: não pode ser vazio
            if (usuarioDigitado.isEmpty() || senhaDigitada.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                // Chama nossa função que simula a API (MOCK)
                if (validarLoginMock(usuarioDigitado, senhaDigitada)) {
                    // SUCESSO: Vai para a Dashboard
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish() // Fecha o login para não voltar com o botão "Voltar" do celular
                } else {
                    // ERRO: Mostra o AlertDialog exigido no trabalho
                    mostrarErroLogin()
                }
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
