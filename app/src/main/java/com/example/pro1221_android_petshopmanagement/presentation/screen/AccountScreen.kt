package com.example.pro1221_android_petshopmanagement.presentation.screen

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.FloatingActionButtonDefaults
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
import com.example.pro1221_android_petshopmanagement.common.collections.isValidEmail
import com.example.pro1221_android_petshopmanagement.common.collections.parseLongTimeToString
import com.example.pro1221_android_petshopmanagement.data.data_source.firebase.CommonData
import com.example.pro1221_android_petshopmanagement.data.data_source.firebase.UserData
import com.example.pro1221_android_petshopmanagement.data.data_source.firebase.changeProfile
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.card.ProgressDialog
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
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
    var isChangeProfileButtonShowing by remember {
        mutableStateOf(false)
    }

    var isSyncingDialogShowing by remember {
        mutableStateOf(false)
    }
    var isEmailTextInputEnabled by remember {
        mutableStateOf(false)
    }
    var isDisplayNameInputEnabled by remember {
        mutableStateOf(false)
    }
    var isValidEmail by remember {
        mutableStateOf(true)
    }
    var imageClickable = remember {
        mutableStateOf(false)
    }
    var imageUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher: ManagedActivityResultLauncher<String, Uri?> = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        imageUri.value = it
    }


    val currentUser = FirebaseAuth.getInstance().currentUser
    val firebaseStorage = Firebase.storage
    var profileDownloadLink:MutableState<Uri?> = remember {
        mutableStateOf(null)
    }
    var userDisplayName by remember {
        mutableStateOf(currentUser?.displayName ?: currentUser?.uid)
    }
    val userSignUpDate = currentUser?.metadata?.creationTimestamp
    var userEmail by remember {
        mutableStateOf(currentUser?.email)
    }
    var userImageUrl = remember {
        mutableStateOf(profileDownloadLink.value ?: currentUser?.photoUrl)
    }
    Log.d("image url", "AccountScreen: ${userImageUrl.value}")
    firebaseStorage.reference.child("user/image_profile/${currentUser?.uid}.jpg").downloadUrl.addOnSuccessListener {
        profileDownloadLink.value = it
        userImageUrl.value = it
    }
    Log.d("image url", "AccountScreen: ${userImageUrl.value}")

    if (imageUri.value != null) {
        userImageUrl.value = imageUri.value!!
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
                            DropdownMenuItem(
                                onClick = {
                                    isSyncingDialogShowing = true
                                    val currentUserUid =
                                        FirebaseAuth.getInstance().currentUser!!.uid
                                    GlobalScope.launch {
                                        CommonData(context = context).apply {
                                            syncWhenPressSync()
                                        }
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
                            DropdownMenuItem(onClick = {
                                isDropdownMenuDisplay.value = false
                                isChangeProfileButtonShowing = true
                                isDisplayNameInputEnabled = true
                                imageClickable.value = true
                            }) {
                                Text(text = "Thay đổi thông tin cá nhân")
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
                if (!imageClickable.value) {
                    Box(modifier = Modifier.wrapContentSize()) {
                        Image(
                            rememberImagePainter(
                                data = userImageUrl.value
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .width(75.dp)
                                .height(75.dp)
                                .clip(CircleShape)
                                .border(width = 2.dp, color = Color.Gray, shape = CircleShape)
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .wrapContentSize(),
                    ) {
                        Card(onClick = {
                            launcher.launch("image/*")
                            Log.d("image uri", "AccountScreen: ${userImageUrl.value}")
                        }, elevation = 0.dp) {
                            Image(
                                painter = rememberImagePainter(
                                    data = userImageUrl.value
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(75.dp)
                                    .height(75.dp)
                                    .clip(CircleShape)
                                    .border(width = 2.dp, color = Color.Gray, shape = CircleShape)
                            )
                        }
                    }

                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    if (!userDisplayName.isNullOrBlank()) {
                        Text(
                            text = currentUser?.displayName ?: currentUser?.uid!!,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal,
                            fontSize = 22.sp
                        )
                    } else {
                        Text(
                            text = currentUser?.uid!!,
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
                // lmfao look at this
                value = userDisplayName!!,
                onValueChange = { userDisplayName = it },
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
                enabled = isDisplayNameInputEnabled
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = userEmail!!,
                onValueChange = {
                    userEmail = it
                    isValidEmail = true
                },
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
                enabled = isEmailTextInputEnabled
            )
            if (!isValidEmail) {
                Text(text = "Không đúng định dạng email!", color = Color.Red)
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (isChangeProfileButtonShowing) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material3.ExtendedFloatingActionButton(
                        onClick = {
                            if (!isValidEmail(email = userEmail!!)) {
                                isValidEmail = false
                                return@ExtendedFloatingActionButton
                            }
                            if (userImageUrl.value == null) {
                                Toast.makeText(
                                    context,
                                    "Bạn chưa chọn hình ảnh!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@ExtendedFloatingActionButton
                            }
                            changeProfile(
                                photoUri = userImageUrl.value!!,
                                displayName = userDisplayName!!,
                                onProfileChangeSuccess = {
                                    isChangeProfileButtonShowing = false
                                    isDisplayNameInputEnabled = false
                                    isChangeProfileButtonShowing = false
                                    isEmailTextInputEnabled = false
                                    imageClickable.value = false
                                    Toast.makeText(
                                        context,
                                        "Thay đổi thông tin thành công!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                context = context
                            )
                        },
                        containerColor = colorResource(id = R.color.copper),
                        contentColor = colorResource(id = R.color.white),
                        text = {
                            Text(
                                text = "Thay đổi",
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Normal
                            )
                        },
                        shape = RoundedCornerShape(32.dp),
                        elevation = FloatingActionButtonDefaults.elevation(
                            defaultElevation = 0.dp,
                            hoveredElevation = 0.dp
                        )
                    )
                }
            }
        }
    }
}