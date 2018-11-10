package com.github.ranaice.listadecompras

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val productAdapter = ProductAdapter(this)
        listViewProducts.adapter = productAdapter

        btnRegisterProduct.setOnClickListener {
            startActivity<CadastroActivity>()
        }

        listViewProducts.setOnItemLongClickListener { _, _, position, _ ->
            productAdapter.remove(productAdapter.getItem(position))

            true
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        database.use {
            select("products").exec {
                // Creating the parser the will map the result to a product object
                val parser = rowParser {
                    // DB Columns
                    id: Int, name: String, quantity: Int, price: Double, photo: ByteArray? ->

                    // Creating the product object from the DB data
                    Product(id, name, quantity, price, photo?.toBitmap())
                }

                // Mapping rows to a list pf products
                val productList = parseList(parser)

                val adapter = listViewProducts.adapter as ProductAdapter

                adapter.clear()
                adapter.addAll(productList)

                val sum = productList.sumByDouble { it.price * it.quantity }
                val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
                txtTotalPrice.text = "TOTAL: ${numberFormat.format(sum)}"
            }
        }
    }
}
