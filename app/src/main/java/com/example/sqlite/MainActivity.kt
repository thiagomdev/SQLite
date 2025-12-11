package com.example.sqlite

import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sqlite.database.DatabaseHandler
import com.example.sqlite.databinding.ActivityMainBinding
import com.example.sqlite.entity.Cadastro

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataBase: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataBase = DatabaseHandler(this)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun btnInclusionClick(view: View) {
        val cadastro = Cadastro(
            _id = 0,
            name = binding.etNome.text.toString(),
            telefone = binding.etTelefone.text.toString()
        )
        dataBase.insert(cadastro)

        Toast.makeText(
            this,
            "Registro inserido com sucesso!",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun btnUpdateOnClick(view: View) {
        val id = binding.etCod.text.toString()
        if (id.isBlank()) {
            Toast.makeText(
                this,
                "Por favor, informe o código.",
                Toast.LENGTH_SHORT).show()
            return
        }

        val cadastro = Cadastro(
            _id = id.toInt(),
            name = binding.etNome.text.toString(),
            telefone = binding.etTelefone.text.toString()
        )

        dataBase.update(cadastro)

        Toast.makeText(
            this,
            "Registro alterado com sucesso!",
            Toast.LENGTH_SHORT
        )
    }

    fun btnDeleteOnClick(view: View) {
        val id = binding.etCod.text.toString()

        if (id.isBlank()) {
            Toast.makeText(
                this,
                "Por favor, informe o código.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        dataBase.delete(id.toInt())

        Toast.makeText(
            this,
            "Registro excluído com sucesso!",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun btnSearchOnClick(view: View) {
        val id = binding.etCod.text.toString()
        if (id.isBlank()) {
            Toast.makeText(
                this,
                "Por favor, informe o código.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val cadastro = dataBase.search(id.toInt())

        if (cadastro != null) {
            binding.etNome.setText(cadastro.name)
            binding.etTelefone.setText(cadastro.telefone)
        } else {
            Toast.makeText(
                this,
                "Registro não encontrado.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun btnListarOnClick(view: View) {
        val cursor: Cursor = dataBase.list()

        if (cursor.moveToFirst()) {
            val stringBuilder = StringBuilder()
            do {
                val idIndex = cursor.getColumnIndex("_id")
                val nameIndex = cursor.getColumnIndex("nome")
                val phoneIndex = cursor.getColumnIndex("telefone")

                val id = cursor.getInt(idIndex)
                val name = cursor.getString(nameIndex)
                val phone = cursor.getString(phoneIndex)

                stringBuilder.append("ID: $id\n")
                stringBuilder.append("Nome: $name\n")
                stringBuilder.append("Telefone: $phone\n\n")
            } while (cursor.moveToNext())

            cursor.close()

            if (stringBuilder.isNotEmpty()) {
                Toast.makeText(
                    this,
                    stringBuilder.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
