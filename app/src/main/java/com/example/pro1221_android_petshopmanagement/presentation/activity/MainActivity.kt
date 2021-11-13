package com.example.pro1221_android_petshopmanagement.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.AppBar
import com.example.pro1221_android_petshopmanagement.ui.screen.component.Drawer
import com.example.pro1221_android_petshopmanagement.ui.screen.navigation.DrawerNavigation
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }
}


@ExperimentalMaterial3Api
@Preview
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun MainContent() {
    // system bar color
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = colorResource(id = R.color.copper)
    )

    // scaffold State
    val scaffoldState = rememberScaffoldState()

    // coroutine scope
    val scope = rememberCoroutineScope()

    // nav host
    val navController = rememberNavController()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                title = "My Pet Shop",
                leftIcon = Icons.Filled.Menu,
                rightIcon = Icons.Filled.AccountCircle,
                scope = scope,
                scaffoldState = scaffoldState
            )
        },
        drawerContent = { Drawer(scaffoldState = scaffoldState, navController = navController) },
        drawerShape = RoundedCornerShape(
            topEnd = 32.dp,
            bottomEnd = 32.dp
        ),
        ) {
        DrawerNavigation(navController = navController)
    }
}
