package br.com.pokedex.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.pokedex.R
import br.com.pokedex.adapter.PokemonAdapter
import br.com.pokedex.model.Habilidade
import br.com.pokedex.model.Pokemon
import br.com.pokedex.model.TipoPokemon

class PesquisaHabilidadeActivity : AppCompatActivity() {

    private lateinit var rvResultado: RecyclerView
    private lateinit var txtVazio: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa_habilidade)

        supportActionBar?.title = "Pesquisar por Habilidade"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvResultado = findViewById(R.id.rvResultadoHab)
        txtVazio = findViewById(R.id.txtSemResultadosHab)
        val spinner = findViewById<Spinner>(R.id.spinnerPesquisaHabilidade)
        val btnPesquisar = findViewById<Button>(R.id.btnPesquisarHab)

        rvResultado.layoutManager = LinearLayoutManager(this)

        // 1. Popula o Spinner com as Habilidades
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Habilidade.values())
        spinner.adapter = adapterSpinner

        // 2. Ação do Botão Pesquisar
        btnPesquisar.setOnClickListener {
            val habilidadeSelecionada = spinner.selectedItem as Habilidade
            realizarPesquisa(habilidadeSelecionada)
        }
    }

    private fun realizarPesquisa(habilidadeAlvo: Habilidade) {
        val todos = criarListaMock()

        // --- FILTRO LÓGICO DIFERENTE ---
        // Como 'it.habilidade' é uma LISTA, verificamos se ela CONTÉM o alvo
        val filtrados = todos.filter { it.habilidade.contains(habilidadeAlvo) }

        if (filtrados.isEmpty()) {
            rvResultado.visibility = View.GONE
            txtVazio.visibility = View.VISIBLE
        } else {
            rvResultado.visibility = View.VISIBLE
            txtVazio.visibility = View.GONE

            val adapter = PokemonAdapter(filtrados) { pokemon ->
                val intent = Intent(this, DetalhesActivity::class.java)
                intent.putExtra("POKEMON_SELECIONADO", pokemon)
                startActivity(intent)
            }
            rvResultado.adapter = adapter
        }
    }

    private fun criarListaMock(): List<Pokemon> {
        return listOf(
            Pokemon("Charmander", TipoPokemon.FOGO, listOf(Habilidade.FOGO), "Ash"),
            // Bulbasaur tem duas habilidades para testarmos se ele aparece nas duas pesquisas
            Pokemon("Bulbasaur", TipoPokemon.PLANTA, listOf(Habilidade.PLANTA, Habilidade.VENENOS), "Misty"),
            Pokemon("Squirtle", TipoPokemon.AGUA, listOf(Habilidade.AGUA), "Brock"),
            Pokemon("Pikachu", TipoPokemon.ELETRICO, listOf(Habilidade.ELETRICO), "Ash"),
            Pokemon("Gengar", TipoPokemon.FANTASMA, listOf(Habilidade.VENENOS), "Gary")
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}