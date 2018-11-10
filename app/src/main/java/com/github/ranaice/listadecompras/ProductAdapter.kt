package com.github.ranaice.listadecompras

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(context: Context) : ArrayAdapter<Product>(context, 0) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_view_item, parent, false)

        val item = getItem(position)
        fillViews(view, item as Product)


        return view
    }

    private fun fillViews(view: View, item: Product) {
        val txtName = view.findViewById<TextView>(R.id.txtProductName)
        val txtQuantity = view.findViewById<TextView>(R.id.txtProductQuantity)
        val txtPrice = view.findViewById<TextView>(R.id.txtProductPrice)
        val imgProduct = view.findViewById<ImageView>(R.id.imgProductPhoto)

        txtName.text = item.name
        txtQuantity.text = item.quantity.toString()
        txtPrice.text = NumberFormat.getCurrencyInstance(Locale("pt", "br")).format(item.price)

        if (item.photo != null) {
            imgProduct.setImageBitmap(item.photo)
        }
    }
}