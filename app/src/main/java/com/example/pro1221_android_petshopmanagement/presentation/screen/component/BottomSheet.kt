package com.example.pro1221_android_petshopmanagement.presentation.screen.component

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet.AddPetViewModel
import com.example.pro1221_android_petshopmanagement.presentation.util.AddPetEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


// FIXME: 11/12/21 Fix this one
//@ExperimentalMaterialApi
//@Composable
//fun BottomSheetAddCustomer(bottomSheetScaffoldState: BottomSheetScaffoldState) {
//    Column() {
//        Spacer(modifier = Modifier.height(32.dp))
//        BottomSheetHeader(title = "Header", bottomSheetScaffoldState = bottomSheetScaffoldState)
//        Spacer(modifier = Modifier.height(16.dp))
//        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
//            BottomSheetImage()
//        }
//        Column(modifier = Modifier.padding(8.dp)) {
//            Spacer(modifier = Modifier.height(16.dp))
//            BottomSheetInput(label = "Name")
//            Spacer(modifier = Modifier.height(8.dp))
//            BottomSheetInput(label = "Name")
//            Spacer(modifier = Modifier.height(8.dp))
//            BottomSheetInput(label = "Name")
//        }
//    }
//}

//@Preview
@Composable
fun BottomSheetPetImage(context: Context, addPetViewModel: AddPetViewModel, scope: CoroutineScope) {
    var imageBitmap: MutableState<Bitmap?> = remember {
        mutableStateOf<Bitmap?>(null)
    }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher: ManagedActivityResultLauncher<String, Uri?> = rememberLauncherForActivityResult(
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

    val tempBitmap: ImageBitmap? = imageBitmap.value?.asImageBitmap()

    SideEffect {
        scope.launch {
            imageBitmap.value?.let {
                addPetViewModel.onEvent(AddPetEvent.EnteredImage(it))
            }
        }
    }

    // FIXME: 11/15/21 fix if possible
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
                bitmap = tempBitmap,
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

@Composable
fun FABPickImage(onImagePick: () -> Unit) {


    androidx.compose.material3.FloatingActionButton(
        onClick = onImagePick,
        containerColor = colorResource(id = R.color.maccaroni_and_cheese),
        modifier = Modifier
            .width(44.dp)
            .height(44.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_outline_edit_24),
            contentDescription = null
        )
    }
}

//@Preview
@Composable
fun BottomSheetFAB() {
    androidx.compose.material3.FloatingActionButton(
        onClick = { /*TODO*/ },
        containerColor = colorResource(id = R.color.maccaroni_and_cheese),
        modifier = Modifier
            .width(44.dp)
            .height(44.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_outline_edit_24),
            contentDescription = null
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun BottomSheetHeaderAddPet(
    title: String,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    viewModel: AddPetViewModel,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    // focus manager to hide keyboard when input done
    val focusManager: FocusManager = LocalFocusManager.current
    Card(
        elevation = 0.dp,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
    ) {
        CenterAlignedTopAppBar(

            title = {
                Text(
                    text = title,
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
                                viewModel.name.value.isBlank() -> {
                                    Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show()
                                    return@launch
                                }
                                viewModel.detail.value.isBlank() -> {
                                    Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show()
                                    return@launch
                                }
                                viewModel.kind.value.isBlank() -> {
                                    Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show()
                                    return@launch
                                }
                                viewModel.price.value.toString().isBlank() -> {
                                    Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show()
                                    return@launch
                                }
                                viewModel.image.value == null -> {
                                    Toast.makeText(context, "image is null", Toast.LENGTH_SHORT)
                                        .show()
                                    return@launch
                                }
                            }

                            // add pet to database and reset field
                            viewModel.onEvent(AddPetEvent.SavePet)
                            viewModel.onEvent(AddPetEvent.EnteredName(""))
                            viewModel.onEvent(AddPetEvent.EnteredDetail(""))
                            viewModel.onEvent(AddPetEvent.EnteredPrice(0))
                            viewModel.onEvent(AddPetEvent.EnteredKind(""))
                            // reset image
                            viewModel.onEvent(
                                AddPetEvent.EnteredImage(
                                    Bitmap.createBitmap(
                                        1,
                                        1,
                                        Bitmap.Config.ARGB_8888
                                    )
                                )
                            )
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                            focusManager.clearFocus()
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
}


@ExperimentalMaterialApi
@Composable
fun BottomSheetAddPet(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    addPetViewModel: AddPetViewModel = hiltViewModel(),
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val name = addPetViewModel.name.value
    val kind = addPetViewModel.kind.value
    val price = addPetViewModel.price.value
    val detail = addPetViewModel.detail.value

    // focus manager
    val focusManager = LocalFocusManager.current

    // context
    val context = LocalContext.current


    Column {
        Spacer(modifier = Modifier.height(32.dp))
        BottomSheetHeaderAddPet(
            title = "Thêm thú mới",
            bottomSheetScaffoldState = bottomSheetScaffoldState,
            viewModel = addPetViewModel
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            BottomSheetPetImage(context = context, addPetViewModel = addPetViewModel, scope = scope)
        }
        Spacer(modifier = Modifier.height(16.dp))
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
        OutlinedTextField(
            value = kind,
            onValueChange = {
                scope.launch {
                    addPetViewModel.onEvent(AddPetEvent.EnteredKind(it))
                }
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.h6,
            label = {
                Text(
                    text = "Loài",
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
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = price.toString(),
            onValueChange = {
                scope.launch {
                    addPetViewModel.onEvent(AddPetEvent.EnteredPrice(it.trim().toInt()))
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
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@ExperimentalMaterialApi
//@Preview
@Composable
fun AddPetBottomSheetPrev() {
    BottomSheetAddPet(bottomSheetScaffoldState = rememberBottomSheetScaffoldState())
}





