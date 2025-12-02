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

class PesquisaTipoActivity : AppCompatActivity() {

    private lateinit var rvResultado: RecyclerView
    private lateinit var txtVazio: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa_tipo)

        supportActionBar?.title = "Pesquisar por Tipo"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Configurações Iniciais
        rvResultado = findViewById(R.id.rvResultadoTipo)
        txtVazio = findViewById(R.id.txtSemResultadosTipo)
        val spinner = findViewById<Spinner>(R.id.spinnerPesquisaTipo)
        val btnPesquisar = findViewById<Button>(R.id.btnPesquisarTipo)

        rvResultado.layoutManager = LinearLayoutManager(this)

        // Popula o Spinner com os Tipos
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, TipoPokemon.values())
        spinner.adapter = adapterSpinner

        // Ação do Botão Pesquisar
        btnPesquisar.setOnClickListener {
            val tipoSelecionado = spinner.selectedItem as TipoPokemon
            realizarPesquisa(tipoSelecionado)
        }
    } // end onCreate

    private fun realizarPesquisa(tipo: TipoPokemon) {
        // Pega todos os pokémons (Mock)
        val todos = criarListaMock()

        // FILTRO: Pega apenas quem tem o tipo igual ao selecionado
        val filtrados = todos.filter { it.tipoPokemon == tipo }

        if (filtrados.isEmpty()) {
            rvResultado.visibility = View.GONE
            txtVazio.visibility = View.VISIBLE
        } else {
            rvResultado.visibility = View.VISIBLE
            txtVazio.visibility = View.GONE

            // Reutiliza o Adapter pra aproveitar os detalhes
            // Ao clicar, também vai para detalhes
            val adapter = PokemonAdapter(filtrados) { pokemon ->
                val intent = Intent(this, DetalhesActivity::class.java)
                intent.putExtra("POKEMON_SELECIONADO", pokemon)
                startActivity(intent)
            }
            rvResultado.adapter = adapter
        }
    }

    // Mesma lista Mock da outra tela
    private fun criarListaMock(): List<Pokemon> {
        return listOf(
            Pokemon("Charmander", TipoPokemon.FOGO, listOf(Habilidade.FOGO), "Ash"),
            Pokemon("Charmeleon", TipoPokemon.FOGO, listOf(Habilidade.FOGO), "Ash"),
            Pokemon("Bulbasaur", TipoPokemon.PLANTA, listOf(Habilidade.PLANTA), "Misty"),
            Pokemon("Squirtle", TipoPokemon.AGUA, listOf(Habilidade.AGUA), "Brock"),
            Pokemon("Pikachu", TipoPokemon.ELETRICO, listOf(Habilidade.ELETRICO), "Ash")
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}