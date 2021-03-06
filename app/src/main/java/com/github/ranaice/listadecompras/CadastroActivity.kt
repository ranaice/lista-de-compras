package com.github.ranaice.listadecompras

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_cadastro.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.toast

class CadastroActivity : AppCompatActivity() {

    private val COD_IMAGE = 101
    var imageBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        btnAddProduct.setOnClickListener {
            val name = txtProductNameRegister.text.toString()
            val quantity = txtProductQuantityRegister.text.toString()
            val price = txtProductPriceRegister.text.toString()

            if (name.isNotBlank() && quantity.isNotBlank() && price.isNotBlank()) {

                database.use {
                    val productId = insert("products",
                        "name" to name,
                        "quantity" to quantity,
                        "price" to price.toDouble(),
                        "photo" to imageBitmap?.toByteArray())

                    if (productId != -1L) {
                        toast("Produto inserido com sucesso")
                        txtProductNameRegister.text.clear()
                        txtProductQuantityRegister.text.clear()
                        txtProductPriceRegister.text.clear()
                    }
                    else {
                        toast("Erro ao inserir no bando de dados")
                    }
                }
            } else {
                txtProductNameRegister.error =
                        if (txtProductNameRegister.text.isEmpty()) "Preencha o name do produto" else null
                txtProductPriceRegister.error = if (txtProductPriceRegister.text.isEmpty()) "Preencha o price" else null
                txtProductQuantityRegister.error =
                        if (txtProductQuantityRegister.text.isEmpty()) "Preencha a quantity" else null
            }
        }

        imgProductPhotoRegister.setOnClickListener {
            openGallery()
        }
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        intent.type = "image/*"

        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == COD_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val inputStream = contentResolver.openInputStream(data.data)
                imageBitmap = BitmapFactory.decodeStream(inputStream)
                imgProductPhotoRegister.setImageBitmap(imageBitmap)
            }
        }
    }
}
