package com.example.pro1221_android_petshopmanagement.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.pro1221_android_petshopmanagement.ui.screen.component.LoginMainView

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginMainView()
        }
    }
}

@Preview
@Composable
fun Preview() {
    LoginMainView()
}
