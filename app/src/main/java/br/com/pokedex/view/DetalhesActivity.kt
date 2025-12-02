package br.com.pokedex.view

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.pokedex.R
import br.com.pokedex.model.Pokemon
import br.com.pokedex.model.TipoPokemon

class DetalhesActivity : AppCompatActivity() {

    private lateinit var pokemonAtual: Pokemon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        supportActionBar?.title = "Editar Pokémon"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 1. Receber o objeto enviado pela tela anterior
        // O "as Pokemon" faz a conversão do genérico para o nosso tipo
        pokemonAtual = intent.getSerializableExtra("POKEMON_SELECIONADO") as Pokemon

        // 2. Colocar os dados na tela
        configurarCampos()

        // 3. Fazer os botões funcionarem
        configurarBotoes()
    }

    private fun configurarCampos() {
        val editNome = findViewById<EditText>(R.id.editDetNome)
        val txtUsuario = findViewById<TextView>(R.id.txtDetUsuario)
        val spinnerTipo = findViewById<Spinner>(R.id.spinnerDetTipo)
        val txtHabilidades = findViewById<TextView>(R.id.txtDetHabilidades)

        // Preenche textos simples
        editNome.setText(pokemonAtual.nome)
        txtUsuario.text = pokemonAtual.usuario

        // Formata a lista de habilidades para texto (Ex: "FOGO, VOADOR")
        txtHabilidades.text = pokemonAtual.habilidade.joinToString(", ")

        // Configura o Spinner com todos os tipos possíveis
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, TipoPokemon.values())
        spinnerTipo.adapter = adapter

        // Descobre qual a posição do tipo atual do Pokémon na lista e seleciona
        val posicao = adapter.getPosition(pokemonAtual.tipoPokemon)
        spinnerTipo.setSelection(posicao)
    }

    private fun configurarBotoes() {
        val btnSalvar = findViewById<Button>(R.id.btnSalvarAlteracoes)
        val btnExcluir = findViewById<Button>(R.id.btnExcluir)
        val editNome = findViewById<EditText>(R.id.editDetNome)
        val spinnerTipo = findViewById<Spinner>(R.id.spinnerDetTipo)

        // --- SALVAR ---
        btnSalvar.setOnClickListener {
            // Atualiza os dados do objeto local
            pokemonAtual.nome = editNome.text.toString()
            pokemonAtual.tipoPokemon = spinnerTipo.selectedItem as TipoPokemon

            // Simula o salvamento (futuramente chamará a API)
            Toast.makeText(this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show()
            finish() // Fecha a tela e volta para a lista
        }

        // --- EXCLUIR ---
        btnExcluir.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Confirmar Exclusão")
                .setMessage("Tem certeza que deseja remover o ${pokemonAtual.nome}?")
                .setPositiveButton("Sim, Excluir") { _, _ ->
                    // Simula a exclusão
                    Toast.makeText(this, "Pokémon removido!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }
    }

    // Botão voltar da barra superior
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}