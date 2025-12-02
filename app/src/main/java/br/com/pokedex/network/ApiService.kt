package br.com.pokedex.network

import br.com.pokedex.model.Pokemon
import br.com.pokedex.model.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    // Rota de teste que já existe no seu Python
    @GET("/teste")
    fun testarConexao(): Call<Map<String, String>>

    // --- Rotas Futuras (Vamos criar no Python em breve) ---

    // Login
    @POST("/login")
    fun login(@Body usuario: Usuario): Call<Map<String, String>>

    // Cadastrar Pokémon
    @POST("/pokemons")
    fun cadastrarPokemon(@Body pokemon: Pokemon): Call<Map<String, String>>

    // Listar Todos
    @GET("/pokemons")
    fun listarPokemons(): Call<List<Pokemon>>

    // Detalhes / Exclusão / Edição...
}