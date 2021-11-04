package com.example.pro1221_android_petshopmanagement.ui.screen

sealed class Screen(val route:String) {
    object LoginScreen : Screen(route = "login_screen")
    object SignUpScreen : Screen(route = "sign_up_screen")
}