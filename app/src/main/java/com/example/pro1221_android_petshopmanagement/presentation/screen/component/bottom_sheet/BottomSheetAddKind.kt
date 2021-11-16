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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.kind.AddKindViewModel
import com.example.pro1221_android_petshopmanagement.presentation.util.AddKindEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetAddKind(
    addKindViewModel: AddKindViewModel = hiltViewModel(),
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    scope: CoroutineScope
) {
    val context = LocalContext.current
    val focus = LocalFocusManager.current
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

    val name = addKindViewModel.name
    val description = addKindViewModel.detail
    val image = addKindViewModel.image

    var tempBitmap: ImageBitmap? = imageBitmap.value?.asImageBitmap()

    SideEffect {
        scope.launch {
            imageBitmap.value?.let {
                addKindViewModel.onEvent(AddKindEvent.EnteredImage(it))
            }
        }
    }

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
                    text = "Thêm loài mới",
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
                            addKindViewModel.name.value.isBlank() -> {
                                Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show()
                                return@launch
                            }
                            addKindViewModel.detail.value.isBlank() -> {
                                Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show()
                                return@launch
                            }
                            addKindViewModel.image.value == null -> {
                                Toast.makeText(context, "Image", Toast.LENGTH_SHORT).show()
                                return@launch
                            }
                        }

                        // clear
                        addKindViewModel.onEvent(AddKindEvent.SaveKind)
                        addKindViewModel.onEvent(AddKindEvent.EnteredImage(null))
                        addKindViewModel.onEvent(AddKindEvent.EnteredName(""))
                        addKindViewModel.onEvent(AddKindEvent.EnteredDetail(""))
                        tempBitmap = null
                        imageBitmap.value = null
                        imageUri = null

                        // close bottom sheet
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                        focus.clearFocus()
                        Toast.makeText(context, "Thêm thành công!", Toast.LENGTH_SHORT).show()
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
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = name.value,
            onValueChange = {
                scope.launch {
                    addKindViewModel.onEvent(AddKindEvent.EnteredName(it))
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
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = description.value,
            onValueChange = {
                scope.launch {
                    addKindViewModel.onEvent(AddKindEvent.EnteredDetail(it))
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






