package com.example.pro1221_android_petshopmanagement.ui.collections

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

fun parseBitmapToByteArray(bitmap: Bitmap): ByteArray {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
    return outputStream.toByteArray()
}

fun parseByteArrayToBitmap(byteArray: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}