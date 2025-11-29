package br.com.pokedex.model.Pokemon

data class Pokemon(
    var nome: String,
    var tipoPokemon: TipoPokemon,
    var habilidade: Habilidade,
    var usuario: String
)
