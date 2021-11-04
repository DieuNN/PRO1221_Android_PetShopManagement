package com.example.pro1221_android_petshopmanagement.ui.screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SignUpScreen() {
    // remove status bar color
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.Transparent
    )
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Đăng ký",
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
            onValueChange = { emailInputState = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.password)) },
            value = passwordInputState,
            onValueChange = { passwordInputState = it },
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
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Nhập lại mật khẩu") },
            value = rePasswordInputState,
            onValueChange = { rePasswordInputState = it },
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
        Spacer(modifier = Modifier.height(32.dp))
        Row(horizontalArrangement = Arrangement.Start) {
            Text(text = "Hoặc đăng ký bằng", style = MaterialTheme.typography.h6, fontSize = 14.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp),
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = null
            )
        }
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            androidx.compose.material3.ExtendedFloatingActionButton(
                text = { Text(text = "Đăng ký") },
                icon = { Icon(Icons.Filled.ArrowForward, "") },
                onClick = { /*TODO*/ },
                containerColor = colorResource(id = R.color.copper),
                contentColor = colorResource(id = R.color.white)
            )
        }
    }

}

@Preview
@Composable
fun SignUpPrev() {
    SignUpScreen()
}