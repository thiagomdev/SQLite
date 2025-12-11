package com.example.sqlite

import android.content.ContentValues
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

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
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
        val register = ContentValues()
        register.put("nome", binding.etNome.text.toString())
        register.put("telefone", binding.etTelefone.text.toString())

        dataBase.update(
            "cadastro",
            register,
            "_id = ?",
            arrayOf(binding.etCod.text.toString())
        )

        Toast.makeText(
            this,
            "Alteração efetuada com sucesso!",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun btnDeleteOnClick(view: View) {
        dataBase.delete(
            "cadastro",
            "_id = ?",
            arrayOf(binding.etCod.text.toString())
        )

        Toast.makeText(
            this,
            "Excluido com sucesso!",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun btnPesquisarOnClick(view: View) {}
    fun btnListarOnClick(view: View) {}
}