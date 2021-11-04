package com.example.pro1221_android_petshopmanagement.ui.screen.component

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.ui.activity.MainActivity
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun LoginMainView() {
    // remove status bar color
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.Transparent
    )

    val context = LocalContext.current

    // scroll state
    val scrollState = rememberScrollState()
    // email input state
    var emailInputState by remember {
        mutableStateOf("")
    }

    // password input state and visibility state
    var passwordInputState by rememberSaveable {
        mutableStateOf("")
    }
    var passwordVisibilityState by rememberSaveable {
        mutableStateOf(false)
    }
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
            onValueChange = { emailInputState = it }
        )
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
                          context.startActivity(Intent(context, MainActivity::class.java))
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
            Text(text = "Hoặc đăng nhập bằng", textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Image(
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = null,
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
            )
        }
        Spacer(modifier = Modifier.width(64.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = "Chưa có tài khoản?")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Đăng ký", modifier = Modifier.clickable {

            }, color = colorResource(id = R.color.maccaroni_and_cheese))
        }

    }
}

@Preview
@Composable
fun Prev() {
    LoginMainView()
}