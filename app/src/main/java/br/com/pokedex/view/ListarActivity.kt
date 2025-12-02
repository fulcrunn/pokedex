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

        supportActionBar?.title = "Todos os Pokémons"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val rvPokemons = findViewById<RecyclerView>(R.id.rvPokemons)
        rvPokemons.layoutManager = LinearLayoutManager(this)

        // --- CÓDIGO NOVO: Busca dados da API ---
        carregarPokemonsDaApi(rvPokemons)
    }

    private fun carregarPokemonsDaApi(recyclerView: RecyclerView) {
        br.com.pokedex.network.RetrofitClient.service.listarPokemons().enqueue(object : retrofit2.Callback<List<Pokemon>> {
            override fun onResponse(call: retrofit2.Call<List<Pokemon>>, response: retrofit2.Response<List<Pokemon>>) {
                if (response.isSuccessful) {
                    val listaReal = response.body() ?: emptyList()

                    if (listaReal.isEmpty()) {
                        Toast.makeText(applicationContext, "Nenhum Pokémon encontrado.", Toast.LENGTH_SHORT).show()
                    }

                    // Configura o Adapter com a lista que veio do Python
                    val adapter = PokemonAdapter(listaReal) { pokemonClicado ->
                        val intent = android.content.Intent(this@ListarActivity, DetalhesActivity::class.java)
                        intent.putExtra("POKEMON_SELECIONADO", pokemonClicado)
                        startActivity(intent)
                    }
                    recyclerView.adapter = adapter

                } else {
                    Toast.makeText(applicationContext, "Erro ao carregar: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<List<Pokemon>>, t: Throwable) {
                Toast.makeText(applicationContext, "Erro de conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Faz o botão de voltar (setinha na barra) funcionar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}