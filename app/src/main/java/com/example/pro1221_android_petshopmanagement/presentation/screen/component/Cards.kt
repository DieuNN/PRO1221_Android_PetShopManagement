package com.example.pro1221_android_petshopmanagement.presentation.screen.component

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.common.collections.parseLongTimeToString
import com.example.pro1221_android_petshopmanagement.domain.model.AnimalInfo
import com.example.pro1221_android_petshopmanagement.domain.model.Customer
import com.example.pro1221_android_petshopmanagement.domain.model.Kind
import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import com.example.pro1221_android_petshopmanagement.presentation.activity.AccountActivity
import com.example.pro1221_android_petshopmanagement.presentation.screen.DrawerNavigationItem
import com.example.pro1221_android_petshopmanagement.presentation.screen.Screen
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet.PetEvent
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet.PetViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
    viewModel: PetViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    bottomSheetScaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val updateDate = try {
        parseLongTimeToString(pet.updateTime.toLong())
    } catch (e: NumberFormatException) {
        "!"
    }
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
                                text = pet.detail,
                                color = Color.White,
                            )
                        }
                    }
                    if (!pet.isSold) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    end = 8.dp
                                ),
                            horizontalArrangement = Arrangement.End
                        ) {
                            androidx.compose.material3.OutlinedButton(
                                // FIXME: Change this
                                onClick = { },
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
                                    scope.launch {
                                        viewModel.onEvent(PetEvent.SetPetSold(pet))
                                        viewModel.onEvent(
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
                                onClick = {
                                    scope.launch {
                                        val deletedResult =
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Đã xóa ${pet.name}",
                                                actionLabel = "Hủy",
                                                duration = SnackbarDuration.Short
                                            )
                                        viewModel.onEvent(PetEvent.DeletePet(pet = pet))

                                        if (deletedResult == SnackbarResult.ActionPerformed) {
                                            viewModel.onEvent(PetEvent.RestorePet(pet))
                                        }
                                    }
                                },
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
fun ConfirmExitDialog(
    shouldShow: Boolean = false
) {
    val context = LocalContext.current as Activity?
    var isOpen by remember {
        mutableStateOf(true)
    }
    androidx.compose.material3.AlertDialog(
        title = { Text(text = "Xác nhận thoát", fontSize = 24.sp) },
        text = { Text(text = "Xác nhận đồng bộ dữ liệu và thoát?") },
        onDismissRequest = { isOpen = false },
        confirmButton = {
            TextButton(onClick = { context?.finishAffinity() }) {
                Text(text = "Thoát")
            }
        },
        dismissButton = {
            TextButton(onClick = { isOpen = false }) {
                Text(text = "Hủy")
            }
        },
        containerColor = colorResource(id = R.color.maccaroni_and_cheese)
    )
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





















