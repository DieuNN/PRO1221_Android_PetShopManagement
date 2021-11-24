package com.example.pro1221_android_petshopmanagement.presentation.screen.component.bottom_sheet

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.kind.KindViewModel
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet.AddPetViewModel
import com.example.pro1221_android_petshopmanagement.presentation.util.AddPetEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun BottomSheetAddPet(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    addPetViewModel: AddPetViewModel = hiltViewModel(),
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val name = addPetViewModel.name.value
    var kind = addPetViewModel.kind.value
    val price = addPetViewModel.price.value
    val detail = addPetViewModel.detail.value

    val kindViewModel: KindViewModel = hiltViewModel()
    val kinds = kindViewModel.kindState.value

    // focus manager
    val focusManager = LocalFocusManager.current

    // context
    val context = LocalContext.current

    var imageBitmap: MutableState<Bitmap?> = remember {
        mutableStateOf(null)
    }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher: ManagedActivityResultLauncher<String, Uri?> =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) {
            imageUri = it
        }

    imageUri?.let {
        imageBitmap.value = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, it)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, it)
            ImageDecoder.decodeBitmap(source)
        }
    }

    var tempBitmap: ImageBitmap? = imageBitmap.value?.asImageBitmap()


    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Card(
            elevation = 0.dp,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            CenterAlignedTopAppBar(

                title = {
                    Text(
                        text = "Thêm thú mới",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }

                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_close_24),
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                //validate empty
                                when {
                                    addPetViewModel.name.value.isBlank() -> {
                                        Toast.makeText(
                                            context,
                                            "Bạn chưa nhập tên thú!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@launch
                                    }
                                    addPetViewModel.detail.value.isBlank() -> {
                                        Toast.makeText(
                                            context,
                                            "Bạn chưa nhập giá giá tiền!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@launch
                                    }
                                    addPetViewModel.kind.value.isBlank() -> {
                                        Toast.makeText(
                                            context,
                                            "Bạn chưa chọn loài!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@launch
                                    }
                                    addPetViewModel.price.value.toString().isBlank() -> {
                                        Toast.makeText(
                                            context,
                                            "Giá đang trống!",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        return@launch
                                    }
                                    addPetViewModel.image.value == null -> {
                                        Toast.makeText(
                                            context,
                                            "Bạn chưa chọn ảnh!",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        return@launch
                                    }
                                }

                                // add pet to database and reset field
                                addPetViewModel.onEvent(AddPetEvent.SavePet)
                                addPetViewModel.onEvent(AddPetEvent.EnteredName(""))
                                addPetViewModel.onEvent(AddPetEvent.EnteredDetail(""))
                                addPetViewModel.onEvent(AddPetEvent.EnteredPrice(0))
                                addPetViewModel.onEvent(AddPetEvent.EnteredKind(""))
                                // reset image
                                addPetViewModel.onEvent(
                                    AddPetEvent.EnteredImage(
                                        null
                                    )
                                )
                                imageBitmap.value = null
                                tempBitmap = null
                                imageUri = null
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                                focusManager.clearFocus()
                                Toast.makeText(context, "Thêm thành công!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_check_24),
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(id = R.color.maccaroni_and_cheese)
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            SideEffect {
                scope.launch {
                    imageBitmap.value?.let {
                        addPetViewModel.onEvent(AddPetEvent.EnteredImage(it))
                    }
                }
            }

            Box(
                Modifier
                    .width(120.dp)
                    .height(128.dp)
            ) {
                if (tempBitmap != null) {
                    Image(
                        modifier = Modifier
                            .width(120.dp)
                            .height(120.dp)
                            .clip(CircleShape),
                        bitmap = tempBitmap!!,
                        contentDescription = null,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.End
                ) {
                    FABPickImage(onImagePick = {
                        launcher.launch("image/*")
                    })
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.padding(16.dp)) {
            val isPickKindDialogShowing = remember {
                mutableStateOf(false)
            }
            if (isPickKindDialogShowing.value) {
                Dialog(onDismissRequest = { isPickKindDialogShowing.value = false }) {
                    Card(shape = RoundedCornerShape(24.dp)) {
                        Column {
                            CenterAlignedTopAppBar(
                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = colorResource(id = R.color.maccaroni_and_cheese)
                                ),
                                title = {
                                    Text(text = "Chọn loài")
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        isPickKindDialogShowing.value = false
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = null
                                        )
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyColumn(
                                contentPadding = PaddingValues(bottom = 16.dp, start = 8.dp, end = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(kinds.size) { index: Int ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(32.dp),
                                        onClick = {
                                            scope.launch {
                                                addPetViewModel.onEvent(
                                                    AddPetEvent.EnteredKind(
                                                        kinds[index].name
                                                    )
                                                )
                                                isPickKindDialogShowing.value = false
                                            }
                                        },
                                        elevation = 3.dp,
                                        backgroundColor = colorResource(id = R.color.copper)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 8.dp),
                                            verticalAlignment = CenterVertically,
                                        ) {
                                            Text(
                                                text = kinds[index].name,
                                                fontSize = 22.sp,
                                                fontStyle = FontStyle.Normal,
                                                color = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
//                androidx.compose.material3.AlertDialog(
//                    containerColor = Color.White,
//                    onDismissRequest = { },
//                    confirmButton = {},
//                    title = {
//                        CenterAlignedTopAppBar(
//                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                                containerColor = colorResource(id = R.color.maccaroni_and_cheese)
//                            ),
//                            title = {
//                                Text(text = "Chọn loài")
//                            },
//                            navigationIcon = {
//                                IconButton(onClick = { isPickKindDialogShowing.value = false }) {
//                                    Icon(
//                                        imageVector = Icons.Default.Close,
//                                        contentDescription = null
//                                    )
//                                }
//                            }
//                        )
//                    },
//                    text = {
//
//                        LazyColumn(
//                            contentPadding = PaddingValues(bottom = 8.dp),
//                            verticalArrangement = Arrangement.spacedBy(8.dp)
//                        ) {
//                            items(kinds.size) { index: Int ->
//                                Card(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .height(32.dp),
//                                    onClick = {
//                                        scope.launch {
//                                            addPetViewModel.onEvent(AddPetEvent.EnteredKind(kinds[index].name))
//                                            isPickKindDialogShowing.value = false
//                                        }
//                                    },
//                                    elevation = 3.dp,
//                                    backgroundColor = colorResource(id = R.color.copper)
//                                ) {
//                                    Row(
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .padding(start = 8.dp),
//                                        verticalAlignment = CenterVertically,
//                                    ) {
//                                        Text(
//                                            text = kinds[index].name,
//                                            fontSize = 22.sp,
//                                            fontStyle = FontStyle.Normal,
//                                            color = Color.White
//                                        )
//                                    }
//                                }
//                            }
//                        }
//                    }
//                )
            }
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        scope.launch {
                            addPetViewModel.onEvent(AddPetEvent.EnteredName(it))
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.h6,
                    label = {
                        Text(
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
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(),
                    shape = RoundedCornerShape(64.dp),
                    border = BorderStroke(width = 1.dp, color = Color.Black.copy(.12f)),
                    elevation = 0.dp,
                    onClick = {
                        isPickKindDialogShowing.value = true
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Loài",
                            modifier = Modifier.padding(16.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black.copy(.25f)
                        )
                        OutlinedTextField(
                            value = kind,
                            onValueChange = { kind = it },
                            enabled = false,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = Color.Transparent,
                                disabledTextColor = Color.Black
                            ),
                            textStyle = MaterialTheme.typography.h6
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    value = price.toString(),
                    onValueChange = {
                        scope.launch {
                            try {
                                addPetViewModel.onEvent(AddPetEvent.EnteredPrice(it.trim().toInt()))
                            } catch (e: NumberFormatException) {
                                Toast.makeText(
                                    context,
                                    "Không thể thêm dấu phẩy ở đây!!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.h6,
                    label = {
                        Text(
                            text = "Giá bán",
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
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = detail,
                    onValueChange = {
                        scope.launch {
                            addPetViewModel.onEvent(AddPetEvent.EnteredDetail(it))
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.h6,
                    label = {
                        Text(
                            text = "Ghi chú",
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
                    )
                )
            }
        }
    }
}