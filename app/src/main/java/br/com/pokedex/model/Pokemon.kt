package br.com.pokedex.model

import br.com.pokedex.model.TipoPokemon

data class Pokemon(
    var nome: String,
    var tipoPokemon: TipoPokemon,
    var habilidade: List<Habilidade>,
    var usuario: String
)