package com.example.pro1221_android_petshopmanagement.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun LoginNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        // Login Screen
        composable(route = Screen.LoginScreen.route, content = {
            LoginMainView(navController = navController)
        })
        // SignUp Screen
        composable(
            route = Screen.SignUpScreen.route,
            content = { SignUpScreen(navController = navController) }
        )
    }
}
