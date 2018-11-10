package com.github.ranaice.listadecompras

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

val globalProducts = mutableListOf<Product>()

fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    // Compressing image
    this.compress(android.graphics.Bitmap.CompressFormat.PNG, 0, stream)

    //Transform the Bitmap into an array of bytes so it can be stored in the DB
    return stream.toByteArray()
}

fun ByteArray.toBitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}