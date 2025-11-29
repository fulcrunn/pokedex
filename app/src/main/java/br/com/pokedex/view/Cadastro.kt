package br.com.pokedex.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.pokedex.R
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import br.com.pokedex.model.Habilidade
import br.com.pokedex.model.TipoPokemon

class Cadastro : AppCompatActivity() {

    // Lista temporária para guardar as habilidades que o usuário vai clicando no "+"
    private val habilidadesSelecionadas = mutableListOf<Habilidade>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.title = "Novo Pokémon"
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Botão voltar

        configurarSpinners()
        configurarBotoes()

    } //end onCreate

    private fun configurarSpinners() {
        val spinnerTipo = findViewById<Spinner>(R.id.spinnerTipo)
        val spinnerHabilidades = findViewById<Spinner>(R.id.spinnerHabilidades)

        // Carrega os valores do Enum TipoPokemon no Spinner
        val adapterTipo = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            TipoPokemon.values()
        )
        spinnerTipo.adapter = adapterTipo

        // Carrega os valores do Enum Habilidade no Spinner
        val adapterHabilidade = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            Habilidade.values()
        )
        spinnerHabilidades.adapter = adapterHabilidade
    }

    private fun configurarBotoes() {
        val btnAdd = findViewById<Button>(R.id.btnAddHabilidade)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)
        val spinnerHab = findViewById<Spinner>(R.id.spinnerHabilidades)
        val txtLista = findViewById<TextView>(R.id.txtHabilidadesSelecionadas)
        val editNome = findViewById<EditText>(R.id.editNomePokemon)
        val spinnerTipo = findViewById<Spinner>(R.id.spinnerTipo)

        // --- Lógica do Botão "+" ---
        btnAdd.setOnClickListener {
            val habilidadeEscolhida = spinnerHab.selectedItem as Habilidade
            // Regra: Máximo 3 habilidades
            if (habilidadesSelecionadas.size >= 3) { // verifica tamanho
                Toast.makeText(this, "Máximo de 3 habilidades permitidas", Toast.LENGTH_SHORT).show()
            } else if (habilidadesSelecionadas.contains(habilidadeEscolhida)) { // verifica repetição
                Toast.makeText(this, "Habilidade já adicionada", Toast.LENGTH_SHORT).show()
            } else { // adiciona a habilidade na lista
                habilidadesSelecionadas.add(habilidadeEscolhida)
                atualizarTextoHabilidades(txtLista)
            }
        }

        // --- Lógica do Botão "Cadastrar" ---
        btnSalvar.setOnClickListener {
            val nome = editNome.text.toString() // nome do pokemon
            val tipo = spinnerTipo.selectedItem as TipoPokemon
            // Validações básicas dos campos, inputtext nome e spinner tipo/habilidade
            if (nome.isEmpty()) {
                editNome.error = "Nome é obrigatório"
            } else if (tipo == null) {
                Toast.makeText(this, "Selecione um tipo", Toast.LENGTH_SHORT).show()
            } else if (habilidadesSelecionadas.isEmpty()) {
                Toast.makeText(this, "Selecione pelo menos 1 habilidade", Toast.LENGTH_SHORT).show()
            } else {
                // SUCESSO (Por enquanto apenas Mock/Toast)
                // Futuramente aqui enviaremos para a API
                Toast.makeText(this, "Pokémon $nome cadastrado com sucesso!", Toast.LENGTH_LONG).show()
                finish() // Volta para a Dashboard
            }
        }
    }

    private fun atualizarTextoHabilidades(textView: TextView) {
        // Transforma a lista em texto bonitinho: "Fogo, Voo, Ataque"
        textView.text = "Selecionadas: " + habilidadesSelecionadas.joinToString(", ")
    }

    // Faz o botão de voltar da barra superior funcionar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}//end Cadastro