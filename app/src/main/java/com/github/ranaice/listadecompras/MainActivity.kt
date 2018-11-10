package com.github.ranaice.listadecompras

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
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
        val adapter = listViewProducts.adapter as ProductAdapter

        adapter.clear()
        adapter.addAll(globalProducts)

        val sum = globalProducts.sumByDouble { it.price * it.quantity }
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        txtTotalPrice.text = "TOTAL: ${numberFormat.format(sum)}"
    }
}
