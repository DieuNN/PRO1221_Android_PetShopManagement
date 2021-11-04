package com.example.pro1221_android_petshopmanagement.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun ComposablePreview(){
    MainContent()
}

@ExperimentalAnimationApi
@Composable
fun MainContent() {
}
