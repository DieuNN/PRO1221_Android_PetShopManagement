package com.example.pro1221_android_petshopmanagement.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.data.data_source.firebase.getCurrentUser
import com.example.pro1221_android_petshopmanagement.ui.activity.LoginActivity
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.DelicateCoroutinesApi


class LauncherScreen : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    @DelicateCoroutinesApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LauncherScreenMainView()
                // check if current user exist, if not, login
            Handler(Looper.getMainLooper()).postDelayed({
                if (getCurrentUser() == null) {
                    startActivity(
                        Intent(this, LoginActivity::class.java)
                    )
                    finishAffinity()
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }
            }, 8500)
        }
    }
}


@ExperimentalAnimationApi
@Composable
fun LauncherScreenMainView() {
    AppIconAppearanceAnimate(modifier = Modifier)
}

@ExperimentalAnimationApi
@Composable
fun AppIconAppearanceAnimate(modifier: Modifier) {
    // context
    val context = LocalContext.current

    // remove status bar color
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.Transparent
    )

    //infinity transition
    val infiniteTransition = rememberInfiniteTransition()

    val imageAlpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(
                durationMillis = 4000,
                delayMillis = 100,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.mipmap.app_icon),
            contentDescription = "Icon",
            modifier = Modifier.alpha(imageAlpha)
        )
    }
}


