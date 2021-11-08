package com.example.pro1221_android_petshopmanagement.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.pro1221_android_petshopmanagement.ui.model.Animal
import com.example.pro1221_android_petshopmanagement.ui.screen.component.AnimalInfoItem

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview
@Composable
fun ComposablePreview(){
    MainContent()
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun MainContent() {
    AnimalInfoItem(animalInfo = Animal())
}
