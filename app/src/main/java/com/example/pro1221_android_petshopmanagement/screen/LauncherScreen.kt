package com.example.pro1221_android_petshopmanagement.screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.DelicateCoroutinesApi

class LauncherScreen : ComponentActivity() {
    @DelicateCoroutinesApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LauncherScreenMainView()

        }
    }
}


@DelicateCoroutinesApi
@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalAnimationApi
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LauncherScreenMainView() {
    // context
    val context = LocalContext.current

    // remove status bar color
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.Transparent
    )

    // visible state
    val fadeInAnim by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = 3000,
        )
    )





    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

    }

}

//Image(
//                painter = painterResource(id = R.mipmap.app_icon),
//                contentDescription = ""
//            )



