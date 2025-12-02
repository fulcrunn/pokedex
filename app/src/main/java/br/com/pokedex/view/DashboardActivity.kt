package br.com.pokedex.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu        // Import Adicionado
import android.view.MenuItem    // Import Adicionado
import android.widget.TextView
import android.widget.Toast     // Import Adicionado
import androidx.appcompat.app.AppCompatActivity
import br.com.pokedex.R

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)
        // Configura o título da Action Bar (Barra superior)
        supportActionBar?.title = "Dashboard"

        // Carregar dados (Mock / Falsos por enquanto)
        carregarDadosDashboard()
    }

    private fun carregarDadosDashboard() {
        val txtTotal = findViewById<TextView>(R.id.txtTotalPokemons)
        val txtTipos = findViewById<TextView>(R.id.txtTopTipos)
        val txtHabilidades = findViewById<TextView>(R.id.txtTopHabilidades)

        // Simulando dados vindos da API (Requisito: indicadores na dashboard)
        txtTotal.text = "151"
        txtTipos.text = "1. Fogo\n2. Água\n3. Planta"
        txtHabilidades.text = "1. Choque do Trovão\n2. Lança-Chamas\n3. Jato D'água"
    }

    // ### Configurações do menu ###

    // Cria o menu na tela
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    // Trata os cliques no menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_cadastrar -> {
                val intent = Intent(this, Cadastro::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_listar -> {
                val intent = Intent(this, ListarActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_pesquisar_tipo -> {
                Toast.makeText(this, "Ir para Pesquisa por Tipo", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_pesquisar_habilidade -> {
                Toast.makeText(this, "Ir para Pesquisa por Habilidade", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_sair -> {
                // Requisito: Botão sair deve fechar o aplicativo [cite: 100]
                finishAffinity() // Fecha todas as activities e sai
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}