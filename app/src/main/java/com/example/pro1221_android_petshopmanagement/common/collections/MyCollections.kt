package com.example.pro1221_android_petshopmanagement.ui.collections

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

fun parseBitmapToByteArray(bitmap: Bitmap): ByteArray {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
    return outputStream.toByteArray()
}

fun parseByteArrayToBitmap(byteArray: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun parseLongTimeToString(time: Long): String {
    // FIXME: 11/13/21 Change this in soldPetsScreen
    try {
        val date = Date(time)
        val format = SimpleDateFormat("dd/MM/yyyy")
        return format.format(date)
    } catch (e: Exception) {

    }
    return "!"
}