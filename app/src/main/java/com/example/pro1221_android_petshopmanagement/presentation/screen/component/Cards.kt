package com.example.pro1221_android_petshopmanagement.presentation.screen.component

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.common.collections.parseLongTimeToString
import com.example.pro1221_android_petshopmanagement.domain.model.AnimalInfo
import com.example.pro1221_android_petshopmanagement.domain.model.Customer
import com.example.pro1221_android_petshopmanagement.domain.model.Kind
import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import com.example.pro1221_android_petshopmanagement.presentation.activity.AccountActivity
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.bottom_sheet.FABPickImage
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.customer.CustomerViewModel
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.kind.KindViewModel
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet.EditPetViewModel
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet.PetEvent
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet.PetViewModel
import com.example.pro1221_android_petshopmanagement.presentation.util.AddPetEvent
import com.example.pro1221_android_petshopmanagement.presentation.util.EditPetEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.system.measureTimeMillis

@Composable
fun AppBar(
    title: String,
    leftIcon: ImageVector,
    rightIcon: ImageVector,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    navController: NavController
) {
    val context = LocalContext.current as? Activity
    CenterAlignedTopAppBar(
        title = { Text(text = title, fontSize = 22.sp, fontWeight = FontWeight.Bold) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(
                    imageVector = leftIcon,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = {
                context?.startActivity(Intent(context, AccountActivity::class.java))
            }) {
                Icon(imageVector = rightIcon, contentDescription = null)
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = colorResource(id = R.color.maccaroni_and_cheese)
        )
    )
}


@ExperimentalMaterialApi
@Composable
fun AnimalInfoItem(animalInfo: AnimalInfo) {
    ListItem {
        var expandedState by remember {
            mutableStateOf(false)
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = LinearOutSlowInEasing
                    )
                ),
            shape = RoundedCornerShape(15.dp),
            elevation = 5.dp,
            backgroundColor = colorResource(id = R.color.copper),
            onClick = {
                expandedState = !expandedState
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = rememberImagePainter(
                            data = animalInfo.image
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp)
                            .clip(CircleShape)
                            .border(width = 2.dp, color = Color.Gray, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = animalInfo.name,
                            style = MaterialTheme.typography.caption,
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = animalInfo.updateTime,
                            color = Color.White,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                if (expandedState) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = rememberImagePainter(
                                data = animalInfo.image
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(162.dp)
                        )
                    }
                    Row(Modifier.fillMaxWidth()) {
                        Column {
                            Text(
                                text = "Loài: ${animalInfo.kind}",
                                color = Color.White,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = animalInfo.detail,
                                color = Color.White,
                            )
                        }
                    }
                }
            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun CustomerItem(customer: Customer) {
    ListItem {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            backgroundColor = colorResource(id = R.color.copper),
            elevation = 5.dp,
            shape = RoundedCornerShape(15.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, bottom = 16.dp, end = 16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = customer.image
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp)
                            .clip(CircleShape)
                            .border(width = 2.dp, shape = CircleShape, color = Color.Gray)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = customer.name,
                            style = MaterialTheme.typography.caption,
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Địa chỉ: ${customer.address}",
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Số điện thoại: ${customer.phoneNumber}",
                    color = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PetInfoCard(
    pet: Pet,
    petViewModel: PetViewModel,
    customerViewModel: CustomerViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    bottomSheetScaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    scope: CoroutineScope = rememberCoroutineScope(),
    onDelete: () -> Unit
) {
    val context = LocalContext.current
    val updateDate = try {
        parseLongTimeToString(pet.updateTime.toLong())
    } catch (e: NumberFormatException) {
        "!"
    }
    val isCustomerPickerShowing = remember {
        mutableStateOf(false)
    }
    val isPickingCustomerDone = remember {
        mutableStateOf(false)
    }

    val isEditInfoDialogShowing = remember {
        mutableStateOf(false)
    }

    ListItem {
        var expandedState by remember {
            mutableStateOf(false)
        }

        if (isCustomerPickerShowing.value) {
            val customersViewModel: CustomerViewModel = hiltViewModel()
            val customers = customersViewModel.customerState.value
            val customerName = remember {
                mutableStateOf("")
            }
            androidx.compose.material3.AlertDialog(
                containerColor = Color.White,
                onDismissRequest = { },
                confirmButton = {},
                title = {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = colorResource(id = R.color.maccaroni_and_cheese)
                        ),
                        title = {
                            Text(text = "Chọn khách hàng")
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                isCustomerPickerShowing.value = false
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                },
                text = {

                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(customers.size) { index: Int ->
                            print(customers.size)
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(64.dp),
                                onClick = {
                                    scope.launch {
                                        customerName.value = customers[index].name
                                        isPickingCustomerDone.value = true
                                        petViewModel.onEvent(
                                            PetEvent.SetPetSold(pet)
                                        )
                                        petViewModel.onEvent(
                                            PetEvent.SetUpdateTime(
                                                id = pet.id!!,
                                                System.currentTimeMillis().toString()
                                            )
                                        )
                                        petViewModel.onEvent(
                                            PetEvent.SetCustomerName(
                                                customerName.value,
                                                pet.id!!
                                            )
                                        )
                                    }
                                    isCustomerPickerShowing.value = false
                                    Toast.makeText(
                                        context,
                                        "Bán thành công! Xem ở mục thú đã bán!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                elevation = 3.dp,
                                backgroundColor = colorResource(id = R.color.copper)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = customers[index].name,
                                        fontSize = 22.sp,
                                        fontStyle = FontStyle.Normal,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = LinearOutSlowInEasing
                    )
                ),
            shape = RoundedCornerShape(15.dp),
            elevation = 5.dp,
            backgroundColor = colorResource(id = R.color.copper),
            onClick = {
                expandedState = !expandedState
            }
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = rememberImagePainter(
                            data = pet.image
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp)
                            .clip(CircleShape)
                            .border(width = 2.dp, color = Color.Gray, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = pet.name,
                            style = MaterialTheme.typography.caption,
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Ngày cập nhật: $updateDate",
                            color = Color.White,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                if (expandedState) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = rememberImagePainter(
                                data = pet.image
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(162.dp)
                        )
                    }
                    Row(Modifier.fillMaxWidth()) {
                        Column {
                            Text(
                                text = "Giá: ${pet.price}",
                                color = Color.White,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Loài: ${pet.kind}",
                                color = Color.White,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Ghi chú: ${pet.detail}",
                                color = Color.White,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            if (pet.isSold) {
                                Text(
                                    text = "Khách hàng: ${pet.customerName}",
                                    color = Color.White
                                )
                            }
                        }
                    }
                    if (!pet.isSold) {
                        if (isEditInfoDialogShowing.value) {
                            DialogEditPetInfo(
                                onDismissRequest = {
                                    isEditInfoDialogShowing.value = false
                                },
                                pet = pet
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    end = 8.dp
                                ),
                            horizontalArrangement = Arrangement.End
                        ) {
                            androidx.compose.material3.OutlinedButton(
                                onClick = {
                                    isEditInfoDialogShowing.value = true
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.white),

                                    ),
                                border = BorderStroke(width = 0.dp, color = Color.White)
                            ) {
                                Text(
                                    text = "Sửa thông tin",
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(id = R.color.copper)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            androidx.compose.material3.OutlinedButton(
                                onClick = {
                                    isCustomerPickerShowing.value = true
                                    if (isPickingCustomerDone.value) {
                                        scope.launch {
                                            petViewModel.onEvent(PetEvent.SetPetSold(pet))
                                            petViewModel.onEvent(
                                                PetEvent.SetUpdateTime(
                                                    pet.id!!,
                                                    System.currentTimeMillis().toString()
                                                )
                                            )
                                            Toast.makeText(
                                                context,
                                                "Đã bán thành công! Xem chi tiết ở mục thú đã bán!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.maccaroni_and_cheese),

                                    ),
                                border = BorderStroke(width = 0.dp, color = Color.White)
                            ) {
                                Text(
                                    text = "Bán",
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(id = R.color.copper)
                                )
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    end = 8.dp
                                ),
                            horizontalArrangement = Arrangement.End
                        ) {
                            androidx.compose.material3.OutlinedButton(
                                onClick = onDelete,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.white),

                                    ),
                                border = BorderStroke(width = 0.dp, color = Color.White)
                            ) {
                                Text(
                                    text = "Xóa",
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(id = R.color.copper)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun KindOfAnimalItem(kind: Kind) {
    var extended by remember {
        mutableStateOf(false)
    }
    ListItem {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = FastOutSlowInEasing
                    )
                ),
            backgroundColor = colorResource(id = R.color.copper),
            shape = RoundedCornerShape(15.dp),
            onClick = { extended = !extended }
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(80.dp)
                        .padding(16.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = kind.image
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp)
                            .clip(CircleShape)
                            .border(width = 2.dp, shape = CircleShape, color = Color.Gray)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = kind.name, fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
                if (extended) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = "Ghi chú: ${kind.description}", color = Color.White)
                    }
                }
            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun PetRankItem(pet: Pet, index: Int) {
    ListItem {
        var expanded by remember {
            mutableStateOf(false)
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = LinearOutSlowInEasing
                    )
                ),
            backgroundColor = colorResource(id = R.color.copper),
            onClick = {
                expanded = !expanded
            },
            shape = RoundedCornerShape(15.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Card(
                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp),
                        shape = CircleShape,
                        backgroundColor = colorResource(id = R.color.maccaroni_and_cheese)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = index.toString(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = pet.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )

                }
                Spacer(modifier = Modifier.height(8.dp))
                if (expanded) {
                    Column {
                        Image(
                            painter = rememberImagePainter(data = pet.image),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Giá bán: ${pet.price}",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Ngày bán: ${parseLongTimeToString(pet.updateTime.toLong())}",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ShowEmptyListWarning(text: String) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class, androidx.compose.material.ExperimentalMaterialApi::class)
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

    val petViewModel:PetViewModel = hiltViewModel()

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
    ) {
        val kindViewModel :KindViewModel = hiltViewModel()
        // FIXME: optimize this, code looks like shit lol :)
        // FIXME: Change to normal dialog
        if (isPickKindDialogShowing.value) {
            androidx.compose.material3.AlertDialog(
                containerColor = Color.White,
                onDismissRequest = { },
                confirmButton = {},
                title = {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = colorResource(id = R.color.maccaroni_and_cheese)
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
                },
                text = {

                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(kindViewModel.kindState.value.size) { index: Int ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(32.dp),
                                onClick = {
                                    petKind = kindViewModel.kindState.value[index].name
                                    scope.launch {
                                        editPetViewModel.onEvent(EditPetEvent.EnteredKind(kindViewModel.kindState.value[index].name))
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
                }
            )
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
                    title = { Text(text = "Lmao") },
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
                                    petViewModel.onEvent(PetEvent.SetUpdateTime(pet.id!!, System.currentTimeMillis().toString()))
                                    editPetViewModel.onEvent(EditPetEvent.SaveChange)
                                }
                                Toast.makeText(context, "Thay đổi thành công!", Toast.LENGTH_SHORT)
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
                                Toast.makeText(context, "Nhập sai định dạng!", Toast.LENGTH_SHORT)
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

