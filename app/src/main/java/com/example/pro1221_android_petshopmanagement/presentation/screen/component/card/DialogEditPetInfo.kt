package com.example.pro1221_android_petshopmanagement.presentation.screen.component.card

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
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.R.color
import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.bottom_sheet.FABPickImage
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.kind.KindViewModel
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet.EditPetViewModel
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet.PetEvent
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet.PetViewModel
import com.example.pro1221_android_petshopmanagement.presentation.util.EditPetEvent
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun DialogEditPetInfo(
    onDismissRequest: () -> Unit,
    pet: Pet,
    editPetViewModel: EditPetViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val imageBitmap: MutableState<Bitmap?> = remember {
        mutableStateOf(null)
    }

    val petViewModel: PetViewModel = hiltViewModel()

    val isPickKindDialogShowing = remember {
        mutableStateOf(false)
    }


    editPetViewModel.setDefaultValue(pet = pet)


    // declare variables
    var petImage by remember {
        mutableStateOf(editPetViewModel.image.value)
    }
    var petName by remember {
        mutableStateOf(editPetViewModel.name.value)
    }
    var petKind by remember {
        mutableStateOf(editPetViewModel.kind.value)
    }
    var petPrice by remember {
        mutableStateOf(editPetViewModel.price.value)
    }
    var petDetail by remember {
        mutableStateOf(editPetViewModel.detail.value)
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
    val isImageChanged = remember {
        mutableStateOf(false)
    }

    imageUri?.let {
        imageBitmap.value = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, it)

        } else {
            val source = ImageDecoder.createSource(context.contentResolver, it)
            ImageDecoder.decodeBitmap(source)
        }
        isImageChanged.value = true
    }

    val tempBitmap = remember {
        mutableStateOf(pet.image!!.asImageBitmap())
    }
    if (isImageChanged.value) {
        tempBitmap.value = imageBitmap.value?.asImageBitmap()!!
        petImage = tempBitmap.value.asAndroidBitmap()
    }

    // set full screen property for dialog
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    )
    {
        val kindViewModel: KindViewModel = hiltViewModel()
//        // FIXME: optimize this, code looks like shit lol :)
        if (isPickKindDialogShowing.value) {
            Dialog(
                onDismissRequest = { isPickKindDialogShowing.value = false },
            ) {
                Card(shape = RoundedCornerShape(24.dp)) {
                    Column {
                        CenterAlignedTopAppBar(
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = colorResource(id = color.maccaroni_and_cheese)
                            ),
                            title = {
                                Text(text = "Chọn loài")
                            },
                            navigationIcon = {
                                IconButton(onClick = { isPickKindDialogShowing.value = false }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = null
                                    )
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyColumn(
                            contentPadding = PaddingValues(
                                bottom = 16.dp,
                                start = 8.dp,
                                end = 8.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(kindViewModel.kindState.value.size) { index: Int ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(64.dp),
                                    onClick = {
                                        petKind = kindViewModel.kindState.value[index].name
                                        scope.launch {
                                            editPetViewModel.onEvent(
                                                EditPetEvent.EnteredKind(
                                                    kindViewModel.kindState.value[index].name
                                                )
                                            )
                                            isPickKindDialogShowing.value = false
                                        }
                                    },
                                    elevation = 3.dp,
                                    backgroundColor = colorResource(id = color.copper)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = kindViewModel.kindState.value[index].name,
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

//        }
        }
        Card(
            modifier = Modifier
                .scrollable(
                    state = scrollState,
                    orientation = Orientation.Vertical
                )
                .fillMaxSize(),
            backgroundColor = Color.White,
            shape = RoundedCornerShape(18.dp)
        ) {
            Column {
                CenterAlignedTopAppBar(
                    title = { Text(text = "Chỉnh sửa thông tin", fontWeight = FontWeight.Bold, fontSize = 22.sp) },
                    navigationIcon = {
                        IconButton(onClick = onDismissRequest) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                when {
                                    petName.isBlank() -> {
                                        Toast.makeText(
                                            context,
                                            "Tên không được để trống!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@IconButton
                                    }
                                    petImage == null -> {
                                        Toast.makeText(
                                            context,
                                            "Bạn chưa chọn hình ảnh!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@IconButton
                                    }
                                    petPrice.toString().isBlank() -> {
                                        Toast.makeText(
                                            context,
                                            "Giá đang trống!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@IconButton
                                    }
                                    petDetail.isBlank() -> {
                                        Toast.makeText(
                                            context,
                                            "Bạn chưa nhập chi tiết!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@IconButton
                                    }
                                    petKind.isBlank() -> {
                                        Toast.makeText(
                                            context,
                                            "Bạn chưa chọn loài!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@IconButton
                                    }
                                }

                                scope.launch {
                                    editPetViewModel.onEvent(EditPetEvent.EnteredId(pet.id!!))
                                    petViewModel.onEvent(
                                        PetEvent.SetUpdateTime(
                                            pet.id!!,
                                            System.currentTimeMillis().toString()
                                        )
                                    )
                                    editPetViewModel.onEvent(EditPetEvent.SaveChange)
                                }
                                Toast.makeText(
                                    context,
                                    "Thay đổi thành công!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                // invoke dismiss func
                                onDismissRequest.invoke()
                            }) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = colorResource(id = R.color.maccaroni_and_cheese)
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    SideEffect {
                        scope.launch {
                            imageBitmap.value?.let {
                                petImage = it
                            }
                        }
                    }

                    Box(
                        Modifier
                            .width(120.dp)
                            .height(128.dp)
                    ) {
                        Image(
                            modifier = Modifier
                                .width(120.dp)
                                .height(120.dp)
                                .clip(CircleShape),
                            bitmap = tempBitmap.value,
                            contentDescription = null,
                        )
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
                Column(modifier = Modifier.padding(8.dp)) {
                    OutlinedTextField(
                        value = petName,
                        onValueChange = {
                            petName = it
                            scope.launch {
                                editPetViewModel.onEvent(EditPetEvent.EnteredName(it))
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
                                value = petKind,
                                onValueChange = { petKind = it },
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

//                    OutlinedTextField(
//                        value = petKind,
//                        onValueChange = {
//                            petKind = it
//                            scope.launch {
//                                editPetViewModel.onEvent(EditPetEvent.EnteredKind(it))
//                            }
//                        },
//                        modifier = Modifier.fillMaxWidth(),
//                        textStyle = MaterialTheme.typography.h6,
//                        label = {
//                            Text(
//                                text = "Loài",
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight.Medium,
//                            )
//                        },
//                        shape = RoundedCornerShape(32.dp),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            focusedBorderColor = Color.Black.copy(.4f),
//                            focusedLabelColor = Color.Black,
//                            cursorColor = Color.Black,
//                            unfocusedBorderColor = Color.Black.copy(.25f)
//                        )
//                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = petPrice.toString(),
                        onValueChange = {
                            try {
                                petPrice = it.toInt()
                                scope.launch {
                                    editPetViewModel.onEvent(EditPetEvent.EnteredPrice(it.toInt()))
                                }
                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    "Nhập sai định dạng!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                return@OutlinedTextField
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.h6,
                        label = {
                            Text(
                                text = "Giá",
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = petDetail,
                        onValueChange = {
                            petDetail = it
                            scope.launch {
                                editPetViewModel.onEvent(EditPetEvent.EnteredDetail(it))
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
}