package com.example.pro1221_android_petshopmanagement.ui.screen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.common.collections.isValidEmail
import com.example.pro1221_android_petshopmanagement.common.collections.isValidPassword
import com.example.pro1221_android_petshopmanagement.data.data_source.firebase.signInWithGoogle
import com.example.pro1221_android_petshopmanagement.data.data_source.firebase.signUpWithEmailAndPassword
import com.example.pro1221_android_petshopmanagement.presentation.screen.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SignUpScreen(navController: NavController) {
    // remove status bar color
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.Transparent
    )

    // isError state
    var isEmailValid by remember {
        mutableStateOf(true)
    }
    var isPasswordValid by remember {
        mutableStateOf(true)
    }
    var isPasswordMatch by remember {
        mutableStateOf(true)
    }

    // state
    var emailInputState by remember {
        mutableStateOf("")
    }
    var passwordInputState by remember {
        mutableStateOf("")
    }
    var rePasswordInputState by remember {
        mutableStateOf("")
    }
    var passwordIconState by remember {
        mutableStateOf(false)
    }
    var rePasswordIconState by remember {
        mutableStateOf(false)
    }
    var passwordIconVisibility =
        if (passwordIconState) painterResource(id = R.drawable.ic_baseline_visibility_24) else painterResource(
            id = R.drawable.ic_baseline_visibility_off_24
        )
    var rePasswordIconVisibility =
        if (rePasswordIconState) painterResource(id = R.drawable.ic_baseline_visibility_24) else painterResource(
            id = R.drawable.ic_baseline_visibility_off_24
        )
    // context
    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TopAppBar(
            backgroundColor = Color.White,
            elevation = 0.dp
        ) {
            IconButton(
                onClick = {
                    context.onBackPressed()
                }
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
        Text(
            text = "????ng k??",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.email)) },
            value = emailInputState,
            onValueChange = {
                emailInputState = it
                isEmailValid = true
            },
        )
        if (!isEmailValid) {
            Text(text = "Kh??ng ????ng ?????nh d???ng email!", color = Color.Red)
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.password)) },
            value = passwordInputState,
            onValueChange = {
                passwordInputState = it
                isPasswordValid = true
            },
            trailingIcon = {
                IconButton(onClick = { passwordIconState = !passwordIconState }) {
                    Icon(
                        painter = passwordIconVisibility,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (passwordIconState) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )
        if (!isPasswordValid) {
            Text(text = "M???t kh???u qu?? ng???n!", color = Color.Red)
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Nh???p l???i m???t kh???u") },
            value = rePasswordInputState,
            onValueChange = {
                rePasswordInputState = it
                isPasswordMatch = true
            },
            trailingIcon = {
                IconButton(onClick = { rePasswordIconState = !rePasswordIconState }) {
                    Icon(
                        painter = rePasswordIconVisibility,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (rePasswordIconState) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )
        if (!isPasswordMatch) {
            Text(text = "M???t kh???u kh??ng tr??ng kh???p!", color = Color.Red)
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(horizontalArrangement = Arrangement.Start) {
            Text(text = "Ho???c ????ng k?? b???ng", style = MaterialTheme.typography.h6, fontSize = 14.sp)
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = { signInWithGoogle(context = context) }) {
                Image(
                    modifier = Modifier
                        .width(32.dp)
                        .height(32.dp),
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = null
                )
            }
        }
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            androidx.compose.material3.ExtendedFloatingActionButton(
                text = { Text(text = "????ng k??") },
                icon = { Icon(Icons.Filled.ArrowForward, "") },
                onClick = {
                    // set error text
                    isEmailValid = isValidEmail(email = emailInputState)
                    isPasswordValid = isValidPassword(password = passwordInputState)
                    isPasswordMatch = (passwordInputState == rePasswordInputState)

                    // validate and create user
                    if (!validateSignUpInfo(
                            isEmailValid = isValidEmail(email = emailInputState),
                            isPasswordValid = isValidPassword(password = passwordInputState),
                            isPasswordMatch = (passwordInputState == rePasswordInputState)
                        )
                    ) {
                        return@ExtendedFloatingActionButton
                    } else {
                        synchronized(this) {
                            signUpWithEmailAndPassword(
                                email = emailInputState,
                                password = rePasswordInputState,
                                context = context,
                                navigateOnSuccess = {
                                    navController.navigate(route = Screen.LoginScreen.route)
                                }
                            )
                        }
                    }
                },
                containerColor = colorResource(id = R.color.copper),
                contentColor = colorResource(id = R.color.white)
            )
        }
    }
}

fun validateSignUpInfo(
    isEmailValid: Boolean,
    isPasswordValid: Boolean,
    isPasswordMatch: Boolean
): Boolean {
    return isEmailValid && isPasswordValid && isPasswordMatch
}
