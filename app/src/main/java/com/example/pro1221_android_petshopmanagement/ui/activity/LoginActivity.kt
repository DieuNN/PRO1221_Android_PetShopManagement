package com.example.pro1221_android_petshopmanagement.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.example.pro1221_android_petshopmanagement.ui.screen.LoginMainView
import com.example.pro1221_android_petshopmanagement.ui.screen.LoginNavigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class LoginActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // LoginNavigtion not LoginScreen
            LoginNavigation()
        }
    }
}



