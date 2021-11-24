package com.example.pro1221_android_petshopmanagement.presentation.screen.component.card

import android.app.Activity
import android.content.Intent
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.presentation.activity.AccountActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppBar(
    title: String,
    leftIcon: ImageVector,
    rightIcon: ImageVector,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    navController: NavController
) {
    val context = LocalContext.current as? Activity
    CenterAlignedTopAppBar(
        title = { Text(text = title, fontSize = 22.sp, fontWeight = FontWeight.Bold) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(
                    imageVector = leftIcon,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = {
                context?.startActivity(Intent(context, AccountActivity::class.java))
            }) {
                Icon(imageVector = rightIcon, contentDescription = null)
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = colorResource(id = R.color.maccaroni_and_cheese)
        )
    )
}