package com.example.pro1221_android_petshopmanagement.presentation.activity

import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.rememberNavController
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.data.data_source.firebase.CommonData
import com.example.pro1221_android_petshopmanagement.data.data_source.firebase.UserData
import com.example.pro1221_android_petshopmanagement.data.data_source.firebase.getGoogleSignInConfigure
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.Drawer
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.card.AppBar
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.card.ProgressDialog
import com.example.pro1221_android_petshopmanagement.presentation.screen.navigation.DrawerNavigation
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalMaterialApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    private val mAuth = FirebaseAuth.getInstance()

    private var isProcessDialogShowing: MutableState<Boolean> = mutableStateOf(true)
    private val isSyncDone: MutableState<Boolean> = mutableStateOf(false)


    @DelicateCoroutinesApi
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // sync when login
        GlobalScope.launch {
            CommonData(context = applicationContext).apply {
                syncWhenLogin()
            }
            UserData(
                context = applicationContext,
                showProcessDialog = {
                    isProcessDialogShowing.value = false
                    isSyncDone.value = true
                },
                currentUserUid = mAuth.currentUser!!.uid,
            ).syncWhenLogin()
        }

        setContent {
            MainContent()
            // if syncing, show a progress dialog
            if (isProcessDialogShowing.value) {
                ProgressDialog {
                    isProcessDialogShowing.value = false
                }
            }
        }
        Log.d("MainActivity", "onCreate: ${mAuth.currentUser!!.uid}")

    }

    override fun onBackPressed() {
        this.finishAffinity()
    }
}


@DelicateCoroutinesApi
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
        drawerContent = {
            Drawer(
                scaffoldState = scaffoldState,
                navController = navController
            )
        },
        drawerShape = RoundedCornerShape(
            topEnd = 32.dp,
            bottomEnd = 32.dp
        ),
    ) {
        // navigate between drawer screen
        DrawerNavigation(navController = navController)
    }
}
