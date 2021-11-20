package com.example.pro1221_android_petshopmanagement.presentation.screen

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.presentation.util.AddAnimalInfoEvent
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen() {
    // system bar color
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = colorResource(id = R.color.copper)
    )

    val context = LocalContext.current as Activity

    val isDropdownMenuDisplay = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = "Tài khoản",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { context.onBackPressed() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        isDropdownMenuDisplay.value = true
                    }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                    }
                    Column(Modifier.wrapContentSize(Alignment.TopStart)) {
                        DropdownMenu(
                            expanded = isDropdownMenuDisplay.value,
                            onDismissRequest = { isDropdownMenuDisplay.value = false },
                        ) {
                            // FIXME: Change this pls
                            DropdownMenuItem(
                                onClick = {
                                    isDropdownMenuDisplay.value = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = "Đồng bộ với server")
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = colorResource(id = R.color.maccaroni_and_cheese)
                )
            )
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Image row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // FIXME: Change this
                Image(
                    painter = painterResource(id = R.drawable.sample_doge_img),
                    contentDescription = null,
                    modifier = Modifier
                        .width(75.dp)
                        .height(75.dp)
                        .clip(CircleShape)
                        .border(width = 2.dp, color = Color.Gray, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Lmao",
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                        fontSize = 22.sp
                    )
                    Text(text = "Ngày tham gia: abcxyz", fontSize = 14.sp)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "Nông Ngọc Diệu",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.subtitle1,
                label = {
                    androidx.compose.material.Text(
                        text = "Tên",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                    )
                },
                shape = RoundedCornerShape(32.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black.copy(.4f),
                    focusedLabelColor = Color.Black,
                    cursorColor = Color.Black,
                    unfocusedBorderColor = Color.Black.copy(.25f)
                ),
                enabled = false
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "nongngocdieu20122002@gmail.com",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.subtitle1,
                label = {
                    androidx.compose.material.Text(
                        text = "Email",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                    )
                },
                shape = RoundedCornerShape(32.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black.copy(.4f),
                    focusedLabelColor = Color.Black,
                    cursorColor = Color.Black,
                    unfocusedBorderColor = Color.Black.copy(.25f)
                ),
                enabled = false
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "20/12/2002",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.subtitle1,
                label = {
                    androidx.compose.material.Text(
                        text = "Ngày tham gia",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                    )
                },
                shape = RoundedCornerShape(32.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black.copy(.4f),
                    focusedLabelColor = Color.Black,
                    cursorColor = Color.Black,
                    unfocusedBorderColor = Color.Black.copy(.25f)
                ),
                enabled = false
            )
        }
    }
}