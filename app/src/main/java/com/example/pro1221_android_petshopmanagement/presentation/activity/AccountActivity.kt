package com.example.pro1221_android_petshopmanagement.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pro1221_android_petshopmanagement.presentation.screen.AccountScreen

class AccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AccountScreen()
        }
    }
}

