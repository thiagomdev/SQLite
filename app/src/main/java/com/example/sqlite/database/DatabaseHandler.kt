package com.example.sqlite.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.sqlite.entity.Cadastro

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "bdfile.sqlite"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "cadastro"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "nome"
        const val COLUMN_PHONE = "telefone"
    }

    override fun onCreate(dataBase: SQLiteDatabase?) {
        dataBase?.execSQL(
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " nome TEXT, telefone TEXT)"
        )
    }

    override fun onUpgrade(dataBase: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        dataBase?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(dataBase)
    }

    fun insert(cadastro: Cadastro) {
        val register = ContentValues()
        register.put("nome", cadastro.name)
        register.put("telefone", cadastro.telefone)
        writableDatabase.insert(TABLE_NAME, null, register)
    }

    fun update(cadastro: Cadastro) {
        val register = ContentValues()
        register.put("nome", cadastro.name)
        register.put("telefone", cadastro.telefone)
        writableDatabase.update(TABLE_NAME, register, "$COLUMN_ID = ?", arrayOf(cadastro._id.toString()))
    }

    fun search(id: Int): Cadastro? {
        val cursor: Cursor = writableDatabase.query(
            TABLE_NAME,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var cadastro: Cadastro? = null

        if (cursor.moveToFirst()) {
            val nameIndex = cursor.getColumnIndex("nome")
            val phoneIndex = cursor.getColumnIndex("telefone")
            cadastro = Cadastro(
                id,
                cursor.getString(nameIndex),
                cursor.getString(phoneIndex)
            )
        } else {
            return null
        }
        return cadastro
    }

    fun list(): Cursor {
        val cursor: Cursor = writableDatabase.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        return cursor
    }

    fun delete(id: Int) {
        writableDatabase.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }
}