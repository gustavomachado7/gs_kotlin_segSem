package gustavomachado7.com.github.ecodicas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import gustavomachado7.com.github.ecodicas.R
import gustavomachado7.com.github.ecodicas.model.ItemModel
import gustavomachado7.com.github.ecodicas.viewmodel.ItemsViewModelFactory
import gustavomachado7.com.github.ecodicas.viewmodel.ItemsAdapter
import gustavomachado7.com.github.ecodicas.viewmodel.ItemsViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ItemsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "EcoDicas"


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val itemsAdapter = ItemsAdapter { item ->
            viewModel.removeItem(item)
        }
        recyclerView.adapter = itemsAdapter
        val button = findViewById<Button>(R.id.button)
        val inputTitulo = findViewById<EditText>(R.id.inputTitulo)
        val inputDescricao = findViewById<EditText>(R.id.inputDescricao)

        button.setOnClickListener {

            if (inputTitulo.text.isEmpty() && inputDescricao.text.isEmpty()) {
                inputTitulo.error = "Preencha o título"
                inputDescricao.error = "Preencha a descrição"
                return@setOnClickListener
            }

            if (inputTitulo.text.isEmpty()) {
                inputTitulo.error = "Preencha o título"
                return@setOnClickListener
            }

            if (inputDescricao.text.isEmpty()) {
                inputDescricao.error = "Preencha a descrição"
                return@setOnClickListener
            }

            if (inputTitulo.text.toString().length < 10 && inputDescricao.text.toString().length < 15) {
                inputTitulo.error = "Quantidade mínima de caracteres não atingida para o título"
                inputDescricao.error =
                    "Quantidade mínima de caracteres não atingida para a descrição"
                return@setOnClickListener
            }

            if (inputTitulo.text.toString().length < 10) {
                inputTitulo.error = "Quantidade mínima de caracteres não atingida para o título"
                return@setOnClickListener
            }


            if (inputDescricao.text.toString().length < 15) {
                inputDescricao.error =
                    "Quantidade mínima de caracteres não atingida para a descrição"
                return@setOnClickListener
            }


            val item = ItemModel(
                titulo = inputTitulo.text.toString(),
                descricao = inputDescricao.text.toString()

            )


            viewModel.addItem(item)
            inputTitulo.text.clear()
            inputDescricao.text.clear()
        }
        val viewModelFactory = ItemsViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ItemsViewModel::class.java)

        viewModel.itemsLiveData.observe(this) { items ->
            itemsAdapter.updateItems(items)
        }


    }

}