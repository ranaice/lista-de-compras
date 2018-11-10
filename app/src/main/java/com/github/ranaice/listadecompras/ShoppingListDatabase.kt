package com.github.ranaice.listadecompras

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class ShoppingListDatabase(context: Context) :
    ManagedSQLiteOpenHelper(ctx = context, name = "listaCompras.db", version = 1) {

    // Singleton
    companion object {
        private var instance: ShoppingListDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): ShoppingListDatabase {
            if (instance == null) {
                instance = ShoppingListDatabase(ctx.applicationContext)
            }

            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            "products", true,
            "id" to INTEGER + PRIMARY_KEY + UNIQUE,
            "name" to TEXT,
            "quantity" to INTEGER,
            "price" to REAL,
            "photo" to BLOB
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

val Context.database: ShoppingListDatabase get() = ShoppingListDatabase.getInstance(applicationContext)
