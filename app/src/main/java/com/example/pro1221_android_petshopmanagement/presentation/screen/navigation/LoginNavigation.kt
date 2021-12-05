package com.example.pro1221_android_petshopmanagement.presentation.screen.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.example.pro1221_android_petshopmanagement.presentation.screen.LoginMainView
import com.example.pro1221_android_petshopmanagement.presentation.screen.Screen
import com.example.pro1221_android_petshopmanagement.ui.screen.SignUpScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun LoginNavigation() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        // Login Screen
        composable(route = Screen.LoginScreen.route,
            content = {
                LoginMainView(
                    navController = navController
                )
            },
            popExitTransition = {
                fadeOut(
                    tween(
                        durationMillis = 300
                    )
                ) + slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    ),
                )
            }
        )
        // SignUp Screen
        composable(
            route = Screen.SignUpScreen.route,
            content = {
                SignUpScreen(navController = navController)
            },
            enterTransition = {
//                fadeIn(
//                    tween(
//                        durationMillis = 300
//                    )
//                ) +
                slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = 300
                    ),
                    initialOffsetX = { -300 }

                )
            }
        )
    }
}
