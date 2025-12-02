package br.com.pokedex.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.pokedex.R
import br.com.pokedex.adapter.PokemonAdapter
import br.com.pokedex.model.Habilidade
import br.com.pokedex.model.Pokemon
import br.com.pokedex.model.TipoPokemon

// Activity que popula essa lista (mock)
class ListarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar)

        // Configura a Barra Superior
        supportActionBar?.title = "Todos os Pokémons"
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Botão Voltar

        // 1. Configurar o RecyclerView (activity_listar.xml)
        val rvPokemons = findViewById<RecyclerView>(R.id.rvPokemons)
        rvPokemons.layoutManager = LinearLayoutManager(this) // Lista Vertical padrão

        // 2. Criar dados Falsos (Mock) para teste
        val listaMock = criarListaMock()

        // 3. Conectar o Adapter
        val adapter = PokemonAdapter(listaMock) { pokemonClicado ->
            // Cria a intenção de ir para a tela de Detalhes
            val intent = android.content.Intent(this, DetalhesActivity::class.java)
            // "Pendura" o objeto Pokemon na intent para ele ir junto para a tela de detalhes
            intent.putExtra("POKEMON_SELECIONADO", pokemonClicado)
            startActivity(intent)
        }

        rvPokemons.adapter = adapter
    }

    // Função auxiliar para gerar dados de teste
    private fun criarListaMock(): List<Pokemon> {
        return listOf(
            Pokemon(
                nome = "Charmander",
                tipoPokemon = TipoPokemon.FOGO,
                habilidade = listOf(Habilidade.FOGO),
                usuario = "Ash"
            ),
            Pokemon(
                nome = "Bulbasaur",
                tipoPokemon = TipoPokemon.PLANTA,
                habilidade = listOf(Habilidade.PLANTA, Habilidade.VENENOS),
                usuario = "Misty"
            ),
            Pokemon(
                nome = "Squirtle",
                tipoPokemon = TipoPokemon.AGUA,
                habilidade = listOf(Habilidade.AGUA),
                usuario = "Brock"
            ),
            Pokemon(
                nome = "Pikachu",
                tipoPokemon = TipoPokemon.ELETRICO,
                habilidade = listOf(Habilidade.ELETRICO),
                usuario = "Ash"
            )
        )
    }

    // Faz o botão de voltar (setinha na barra) funcionar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}