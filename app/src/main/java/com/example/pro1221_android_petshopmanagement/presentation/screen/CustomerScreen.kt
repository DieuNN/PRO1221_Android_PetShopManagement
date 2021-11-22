package com.example.pro1221_android_petshopmanagement.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.domain.model.Customer
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.CustomerItem
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.KindOfAnimalItem
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.ShowEmptyListWarning
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.bottom_sheet.BottomSheetAddCustomer
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.customer.CustomerEvent
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.customer.CustomerViewModel
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.kind.KindEvent
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@ExperimentalMaterialApi
@Composable
fun CustomerScreen(customerViewModel: CustomerViewModel = hiltViewModel()) {
    val customerList = mutableStateListOf<Customer>()
    customerViewModel.customerState.value.forEach { customerList.add(it) }
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        sheetContent = {
            Spacer(modifier = Modifier.height(32.dp))
            BottomSheetAddCustomer(
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                scope = scope
            )
            Spacer(modifier = Modifier.height(32.dp))
        },
        floatingActionButton = {
            androidx.compose.material3.FloatingActionButton(
                onClick = {
                    scope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        } else {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }
                },
                containerColor = colorResource(id = R.color.copper)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 32.dp,
        sheetGesturesEnabled = true,
        sheetElevation = 0.dp
    ) {
        if (customerList.isEmpty()) {
            ShowEmptyListWarning(text = "Danh sách đang trống! Thử thêm mới bằng nút ấn phía dưới!")
        } else {
            LazyColumn(
                contentPadding = PaddingValues(top = 8.dp, bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(
                    items = customerList,
                    key = { _: Int, item: Customer ->
                        item.id.hashCode()
                    }
                ) { _: Int, item: Customer ->
                    val state = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                customerList.remove(item)
                                customerViewModel.onEvent(CustomerEvent.DeleteCustomer(item))
                                scope.launch {
                                    val deleteResult =
                                        bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                                            message = "Đã xóa ${item.name}",
                                            actionLabel = "Hủy",
                                            duration = SnackbarDuration.Short
                                        )
                                    if (deleteResult == SnackbarResult.ActionPerformed) {
                                        customerViewModel.onEvent(
                                            CustomerEvent.RestoreCustomer(
                                                customer = item
                                            )
                                        )
                                    }
                                }
                            }
                            true
                        })
                    SwipeToDismiss(
                        state = state,
                        background = {
                            val color = when (state.dismissDirection) {
                                DismissDirection.StartToEnd -> Color.Transparent
                                DismissDirection.EndToStart -> Color.Red
                                null -> Color.Transparent
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = color)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                        },
                        dismissContent = {
                            CustomerItem(customer = item)
                        },
                        directions = setOf(DismissDirection.EndToStart)
                    )
                }
            }
        }
    }
}