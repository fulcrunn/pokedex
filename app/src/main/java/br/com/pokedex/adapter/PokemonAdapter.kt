package br.com.pokedex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.pokedex.R
import br.com.pokedex.model.Pokemon

class PokemonAdapter(
    private val listaPokemons: List<Pokemon>,

    private val onPokemonClick: (Pokemon) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    // Cria o visual da linha (infla o XML)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    // Pega os dados da lista e coloca na tela (Nome, Usuario)
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = listaPokemons[position]
        holder.bind(pokemon, onPokemonClick)
    }

    // Diz pro Android quantos itens tem na lista
    override fun getItemCount(): Int = listaPokemons.size

    // Classe interna que segura os componentes visuais (TextViews)
    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtNome: TextView = itemView.findViewById(R.id.txtNomePokemon)
        private val txtUsuario: TextView = itemView.findViewById(R.id.txtUsuarioCadastro)
        // O card inteiro ou o botão VER pode ser clicável. Vamos usar o item todo.

        fun bind(pokemon: Pokemon, clickListener: (Pokemon) -> Unit) {
            txtNome.text = pokemon.nome
            txtUsuario.text = "Por: ${pokemon.usuario}"

            // Configura o clique no item inteiro
            itemView.setOnClickListener {
                clickListener(pokemon)
            }
        }
    }
}