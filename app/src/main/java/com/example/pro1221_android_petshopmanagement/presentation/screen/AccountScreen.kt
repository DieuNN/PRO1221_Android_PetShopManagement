package com.example.pro1221_android_petshopmanagement.presentation.screen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.common.collections.parseLongTimeToString
import com.example.pro1221_android_petshopmanagement.data.data_source.firebase.UserData
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.card.ProgressDialog
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
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

    var isSyncingDialogShowing by remember {
        mutableStateOf(false)
    }

    val currentUser = FirebaseAuth.getInstance().currentUser
    val userDisplayName = currentUser?.displayName
    val userUid = currentUser?.uid
    val userSignUpDate = currentUser?.metadata?.creationTimestamp
    val userEmail = currentUser?.email
    val userImageUrl = currentUser?.photoUrl

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
                                    isSyncingDialogShowing = true
                                    val currentUserUid =
                                        FirebaseAuth.getInstance().currentUser!!.uid
                                    GlobalScope.launch {
                                        UserData(
                                            context = context,
                                            showProcessDialog = {
                                                isSyncingDialogShowing = false
                                            },
                                            currentUserUid = currentUserUid
                                        ).syncWhenPressSync()
                                    }
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
        if (isSyncingDialogShowing) {
            ProgressDialog {
                isSyncingDialogShowing = false
            }
        }
        Column(modifier = Modifier.padding(16.dp)) {
            // Image row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // FIXME: Change this
                if (userImageUrl == null) {
                    Image(
                        painter = painterResource(id = R.drawable.sample_doge_img),
                        contentDescription = null,
                        modifier = Modifier
                            .width(75.dp)
                            .height(75.dp)
                            .clip(CircleShape)
                            .border(width = 2.dp, color = Color.Gray, shape = CircleShape)
                    )
                } else {
                    Image(
                        painter = rememberImagePainter(
                            data = userImageUrl
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .width(75.dp)
                            .height(75.dp)
                            .clip(CircleShape)
                            .border(width = 2.dp, color = Color.Gray, shape = CircleShape)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    if (userDisplayName.isNullOrBlank()) {
                        Text(
                            text = userUid!!,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal,
                            fontSize = 22.sp
                        )
                    } else {
                        Text(
                            text = userDisplayName,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal,
                            fontSize = 22.sp
                        )
                    }
                    Text(
                        text = "Ngày tham gia: ${parseLongTimeToString(userSignUpDate!!)}",
                        fontSize = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = userDisplayName!!,
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
                value = userEmail!!,
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
                value = parseLongTimeToString(userSignUpDate!!),
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