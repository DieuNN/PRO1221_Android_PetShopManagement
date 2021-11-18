package com.example.pro1221_android_petshopmanagement.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.CustomerItem
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.ShowEmptyListWarning
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.bottom_sheet.BottomSheetAddCustomer
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.customer.CustomerViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun CustomerScreen(customerViewModel: CustomerViewModel = hiltViewModel()) {
    val customerList = customerViewModel.customerState.value
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
                items(customerList.size) { index: Int ->
                    CustomerItem(customer = customerList[index])
                }
            }
        }
    }
}