package com.example.pro1221_android_petshopmanagement.common.collections

import android.content.Context
import android.net.ConnectivityManager
import android.util.Patterns

fun isValidEmail(email: String): Boolean {
    return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPassword(password: String): Boolean = password.length >= 6

fun isNetworkAvailable(context: Context): Boolean? {
    return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo?.isConnectedOrConnecting
}