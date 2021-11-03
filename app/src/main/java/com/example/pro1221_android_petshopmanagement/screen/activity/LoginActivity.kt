package com.example.pro1221_android_petshopmanagement.screen.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pro1221_android_petshopmanagement.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun LoginMainView() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.Transparent
    )

    // scroll state
    val scrollState = rememberScrollState()
    // email input state
    var emailInputState by remember {
        mutableStateOf("")
    }

    // password input state
    var passwordInputState by remember {
        mutableStateOf("")
    }

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
            onValueChange = { passwordInputState = it }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.forgot_password),
                fontSize = 12.sp,
                style = MaterialTheme.typography.caption
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = { /*TODO*/ }, modifier = Modifier
                    .width(140.dp)
                    .height(48.dp),
                border = BorderStroke(1.dp, colorResource(id = R.color.black)),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(colorResource(id = R.color.copper))
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

@Preview(showBackground = true)
@Composable
fun LoginPrev() {
    LoginMainView()
}