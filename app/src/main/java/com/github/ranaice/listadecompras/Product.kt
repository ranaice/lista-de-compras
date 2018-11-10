package com.github.ranaice.listadecompras

import android.graphics.Bitmap

data class Product (val id: Int, val name: String, val quantity: Int, val price: Double, val photo: Bitmap? = null)