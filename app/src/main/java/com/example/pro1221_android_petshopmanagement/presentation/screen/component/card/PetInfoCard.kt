package com.example.pro1221_android_petshopmanagement.presentation.screen.component.card

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.common.collections.parseLongTimeToString
import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.customer.CustomerViewModel
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet.PetEvent
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet.PetViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
            Dialog(onDismissRequest = { isCustomerPickerShowing.value = false }) {
                Card(shape = RoundedCornerShape(16.dp)) {
                    Column {
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
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyColumn(
                            contentPadding = PaddingValues(bottom = 16.dp, start = 8.dp, end = 8.dp),
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
                }

            }
//            androidx.compose.material3.AlertDialog(
//                containerColor = Color.White,
//                onDismissRequest = { },
//                confirmButton = {},
//                title = {
//                    CenterAlignedTopAppBar(
//                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                            containerColor = colorResource(id = com.example.pro1221_android_petshopmanagement.R.color.maccaroni_and_cheese)
//                        ),
//                        title = {
//                            Text(text = "Chọn khách hàng")
//                        },
//                        navigationIcon = {
//                            IconButton(onClick = {
//                                isCustomerPickerShowing.value = false
//                            }) {
//                                Icon(
//                                    imageVector = Icons.Default.Close,
//                                    contentDescription = null
//                                )
//                            }
//                        }
//                    )
//                },
//                text = {
//
//                    LazyColumn(
//                        contentPadding = PaddingValues(bottom = 8.dp),
//                        verticalArrangement = Arrangement.spacedBy(8.dp)
//                    ) {
//                        items(customers.size) { index: Int ->
//                            print(customers.size)
//                            Card(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .height(64.dp),
//                                onClick = {
//                                    scope.launch {
//                                        customerName.value = customers[index].name
//                                        isPickingCustomerDone.value = true
//                                        petViewModel.onEvent(
//                                            PetEvent.SetPetSold(pet)
//                                        )
//                                        petViewModel.onEvent(
//                                            PetEvent.SetUpdateTime(
//                                                id = pet.id!!,
//                                                System.currentTimeMillis().toString()
//                                            )
//                                        )
//                                        petViewModel.onEvent(
//                                            PetEvent.SetCustomerName(
//                                                customerName.value,
//                                                pet.id!!
//                                            )
//                                        )
//                                    }
//                                    isCustomerPickerShowing.value = false
//                                    Toast.makeText(
//                                        context,
//                                        "Bán thành công! Xem ở mục thú đã bán!",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                },
//                                elevation = 3.dp,
//                                backgroundColor = colorResource(id = com.example.pro1221_android_petshopmanagement.R.color.copper)
//                            ) {
//                                Row(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(start = 8.dp),
//                                    verticalAlignment = Alignment.CenterVertically,
//                                ) {
//                                    Text(
//                                        text = customers[index].name,
//                                        fontSize = 22.sp,
//                                        fontStyle = FontStyle.Normal,
//                                        color = Color.White
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//            )
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

