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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.animal.AddAnimalInfoViewModel
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.customer.AddCustomerViewModel
import com.example.pro1221_android_petshopmanagement.presentation.util.AddAnimalInfoEvent
import com.example.pro1221_android_petshopmanagement.presentation.util.AddCustomerEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetAddAnimalInfo(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    addAnimalInfoViewModel: AddAnimalInfoViewModel = hiltViewModel(),
    scope: CoroutineScope
) {
    // FIXME: 12/4/21 reuse code if possible
    val context = LocalContext.current
    val focus = LocalFocusManager.current

    // get image from external storage and display it
    val imageBitmap: MutableState<Bitmap?> = remember {
        mutableStateOf(null)
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
    var tempBitmap: ImageBitmap? = imageBitmap.value?.asImageBitmap()

    SideEffect {
        scope.launch {
            imageBitmap.value?.let {
                addAnimalInfoViewModel.onEvent(AddAnimalInfoEvent.EnteredImage(it))
            }
        }
    }

    // create state
    val name = addAnimalInfoViewModel.name
    val detail = addAnimalInfoViewModel.detail
    val kind = addAnimalInfoViewModel.kind

    Column {
        CenterAlignedTopAppBar(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                ),
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = colorResource(id = R.color.maccaroni_and_cheese)
            ),
            title = {
                Text(
                    text = "Th??m th??ng tin th?? m???i",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                }
            },

            actions = {
                IconButton(onClick = {
                    scope.launch {
                        when {
                            addAnimalInfoViewModel.name.value.isBlank() -> {
                                Toast.makeText(context, "B???n ch??a nh???p t??n!", Toast.LENGTH_SHORT)
                                    .show()
                                return@launch
                            }
                            addAnimalInfoViewModel.detail.value.isBlank() -> {
                                Toast.makeText(
                                    context,
                                    "B???n ch??a th??m chi ti???t!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@launch
                            }
                            addAnimalInfoViewModel.image.value == null -> {
                                Toast.makeText(
                                    context,
                                    "B???n ch??a ch???n h??nh ???nh!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@launch
                            }
                            addAnimalInfoViewModel.kind.value.isBlank() -> {
                                Toast.makeText(context, "B???n ch??a nh???p lo??i!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                        // add to database
                        addAnimalInfoViewModel.onEvent(AddAnimalInfoEvent.SaveAnimalInfo)
                        // clear
                        addAnimalInfoViewModel.onEvent(AddAnimalInfoEvent.EnteredImage(null))
                        addAnimalInfoViewModel.onEvent(AddAnimalInfoEvent.EnteredName(""))
                        addAnimalInfoViewModel.onEvent(AddAnimalInfoEvent.EnteredDetail(""))
                        addAnimalInfoViewModel.onEvent(AddAnimalInfoEvent.EnteredKind(""))

                        tempBitmap = null
                        imageBitmap.value = null
                        imageUri = null

                        // close bottom sheet
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                        focus.clearFocus()
                        Toast.makeText(context, "Th??m th??nh c??ng!", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = null,
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
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
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = name.value,
                    onValueChange = {
                        scope.launch {
                            addAnimalInfoViewModel.onEvent(AddAnimalInfoEvent.EnteredName(it))
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.h6,
                    label = {
                        Text(
                            text = "T??n",
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
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = kind.value,
                    onValueChange = {
                        scope.launch {
                            addAnimalInfoViewModel.onEvent(AddAnimalInfoEvent.EnteredKind(it))
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.h6,
                    label = {
                        Text(
                            text = "Lo??i",
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

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = detail.value,
                    onValueChange = {
                        scope.launch {
                            addAnimalInfoViewModel.onEvent(AddAnimalInfoEvent.EnteredDetail(it))
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.h6,
                    label = {
                        Text(
                            text = "Chi ti???t",
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