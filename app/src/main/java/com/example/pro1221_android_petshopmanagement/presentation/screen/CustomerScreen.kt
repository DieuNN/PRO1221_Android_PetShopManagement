package com.example.pro1221_android_petshopmanagement.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pro1221_android_petshopmanagement.ui.model.Customer
import com.example.pro1221_android_petshopmanagement.ui.model.FakeDataReposotory
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.CustomerItem

@ExperimentalMaterialApi
@Preview
@Composable
fun CustomerScreenPrev() {
    val fakeData = FakeDataReposotory.getCustomer(LocalContext.current)
    LazyColumn(
       verticalArrangement = Arrangement.spacedBy(8.dp )
    ) {
        items(fakeData.size) {index ->
            CustomerItem(customer = fakeData[index])
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun CustomerScreen(customerList:MutableList<Customer>) {
    LazyColumn(contentPadding = PaddingValues(top = 8.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp )
    ) {
        items(customerList.size) {index ->
            CustomerItem(customer = customerList[index])
        }
    }
}