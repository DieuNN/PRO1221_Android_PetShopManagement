package com.example.pro1221_android_petshopmanagement.presentation.screen

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.pro1221_android_petshopmanagement.data.data_source.firebase.forgetPassword
import com.example.pro1221_android_petshopmanagement.data.data_source.firebase.loginWithEmailAndPassword
import com.example.pro1221_android_petshopmanagement.data.data_source.firebase.signInWithGoogle
import com.example.pro1221_android_petshopmanagement.presentation.activity.MainActivity
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@ExperimentalAnimationApi
@OptIn(ExperimentalMaterialApi::class)
@ExperimentalMaterialApi
@Composable
fun LoginMainView(navController: NavController) {
    // remove status bar color
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.Transparent
    )

    val context = LocalContext.current as Activity

    // scroll state
    val scrollState = rememberScrollState()
    // email input state
    var emailInputState by remember {
        mutableStateOf("")
    }

    // is Error state
    var isValidEmail by remember {
        mutableStateOf(true)
    }

    // password input state and visibility state
    var passwordInputState by rememberSaveable {
        mutableStateOf("")
    }
    var passwordVisibilityState by rememberSaveable {
        mutableStateOf(false)
    }
    // set visibility icon
    val visibilityIcon =
        if (passwordVisibilityState) painterResource(id = R.drawable.ic_baseline_visibility_24) else painterResource(
            id = R.drawable.ic_baseline_visibility_off_24
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = stringResource(id = R.string.login),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            style = MaterialTheme.typography.h6
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Image(
                painter = painterResource(id = R.mipmap.app_icon),
                contentDescription = null,
            )
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.email)) },
            value = emailInputState,
            onValueChange = {
                emailInputState = it
                isValidEmail = true
            }
        )
        if (!isValidEmail) {
            Text(text = "Email kh??ng ????ng", color = Color.Red)
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.password)) },
            value = passwordInputState,
            onValueChange = { passwordInputState = it },
            trailingIcon = {
                IconButton(onClick = { passwordVisibilityState = !passwordVisibilityState }) {
                    Icon(
                        painter = visibilityIcon,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (passwordVisibilityState) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.clickable {
                    isValidEmail = isValidEmail(emailInputState)
                    if (isValidEmail) {
                        forgetPassword(email = emailInputState, onSuccessful = {
                            Toast.makeText(
                                context,
                                "M???t email ???? ???????c g???i t???i $emailInputState, ki???m tra email v?? l??m theo h?????ng d???n ????? ?????t l???i m???t kh???u!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }, onFailure = {
                            Toast.makeText(
                                context,
                                "Ki???m tra email v?? th??? l???i! H??y ch???c ch???n r???ng b???n ???? ????ng k??!",
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                    } else {
                        return@clickable
                    }
                },
                text = stringResource(id = R.string.forgot_password),
                fontSize = 12.sp,
                style = MaterialTheme.typography.caption
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = {
                    isValidEmail = isValidEmail(email = emailInputState)
                    if (!validateLoginInfo(
                            email = emailInputState,
                            password = passwordInputState
                        )
                    ) {
                        return@Button
                    } else {
                        loginWithEmailAndPassword(
                            email = emailInputState,
                            password = passwordInputState,
                            context = context,
                            onSuccessful = {
                                context.startActivity(Intent(context, MainActivity::class.java))
                                context.finishAffinity()
                            },
                        )
                    }
                }, modifier = Modifier
                    .width(140.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.copper))
            ) {
                Text(text = stringResource(id = R.string.login), color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "Ho???c ????ng nh???p b???ng", textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            IconButton(onClick = {
                signInWithGoogle(context = context)
            }) {
                Image(
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .width(32.dp)
                        .height(32.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(64.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = "Ch??a c?? t??i kho???n?")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "????ng k??", modifier = Modifier.clickable {
                navController.navigate(route = Screen.SignUpScreen.route)
            }, color = colorResource(id = R.color.maccaroni_and_cheese))
        }
    }
}

fun validateLoginInfo(email: String, password: String): Boolean {
    return isValidEmail(email = email) && isValidPassword(password = password)
}
