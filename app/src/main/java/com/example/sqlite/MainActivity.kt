package com.example.sqlite

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataBase: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataBase = openOrCreateDatabase(
            "bdfile.sqlite",
            MODE_PRIVATE,
            null
        )

        dataBase.execSQL(
            "CREATE TABLE IF NOT EXISTS cadastro (_id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, telefone TEXT)"
        )

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun btnInclusionClick(view: View) {
        val register = ContentValues()
        register.put("nome", binding.etNome.text.toString())
        register.put("telefone", binding.etTelefone.text.toString())

        dataBase.insert("cadastro", null, register)

        Toast.makeText(
            this,
            "Registro inserido com sucesso!",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun btnUpdateOnClick(view: View) {
        val id = binding.etCod.text.toString()
        if (id.isBlank()) {
            Toast.makeText(this, "Por favor, informe o código.", Toast.LENGTH_SHORT).show()
            return
        }

        val register = ContentValues()
        register.put("nome", binding.etNome.text.toString())
        register.put("telefone", binding.etTelefone.text.toString())

        val numRowsUpdated = dataBase.update(
            "cadastro",
            register,
            "_id = ?",
            arrayOf(id)
        )

        if (numRowsUpdated > 0) {
            Toast.makeText(
                this,
                "Alteração efetuada com sucesso!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this,
                "Nenhum registro encontrado com o código informado.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun btnDeleteOnClick(view: View) {
        val id = binding.etCod.text.toString()
        if (id.isBlank()) {
            Toast.makeText(this, "Por favor, informe o código.", Toast.LENGTH_SHORT).show()
            return
        }

        val numRowsDeleted = dataBase.delete(
            "cadastro",
            "_id = ?",
            arrayOf(id)
        )

        if (numRowsDeleted > 0) {
            Toast.makeText(
                this,
                "Excluido com sucesso!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this,
                "Nenhum registro encontrado com o código informado.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun btnSearchOnClick(view: View) {
        val id = binding.etCod.text.toString()
        if (id.isBlank()) {
            Toast.makeText(this, "Por favor, informe o código.", Toast.LENGTH_SHORT).show()
            return
        }

        val cursor: Cursor = dataBase.query(
            "cadastro",
            null,
            "_id = ?",
            arrayOf(id),
            null,
            null,
            null
        )

        cursor.use { c ->
            if (c.moveToFirst()) {
                val nameIndex = c.getColumnIndex("nome")
                val phoneIndex = c.getColumnIndex("telefone")

                if (nameIndex != -1 && phoneIndex != -1) {
                    val name = c.getString(nameIndex)
                    val phone = c.getString(phoneIndex)

                    binding.etNome.setText(name)
                    binding.etTelefone.setText(phone)
                } else {
                    Toast.makeText(this, "Coluna não encontrada.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Registro não encontrado!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun btnListarOnClick(view: View) {}
}
