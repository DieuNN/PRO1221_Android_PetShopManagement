package com.example.pro1221_android_petshopmanagement.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
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
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.Drawer
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.card.AppBar
import com.example.pro1221_android_petshopmanagement.presentation.screen.navigation.DrawerNavigation
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    private val mAuth = FirebaseAuth.getInstance()

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle("Xác nhận thoát")
            setMessage("Xác nhận đồng bộ dữ liệu và thoát?")
            setNegativeButton("Hủy") { dialog, _ ->
                dialog.dismiss()
            }
            setPositiveButton("Thoát") { _, _ ->
                mAuth.signOut()
                Log.d("MainActivity", "onCreate: Signed Out")
                finishAffinity()
            }
        }.create().show()
    }
}


@ExperimentalMaterial3Api
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
                scaffoldState = scaffoldState,
                navController = navController
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
