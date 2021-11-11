package com.example.pro1221_android_petshopmanagement.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet.PetEvent
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet.PetViewModel
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.BottomSheetAddPet
import com.example.pro1221_android_petshopmanagement.ui.screen.component.PetInfoCard
import kotlinx.coroutines.launch
import kotlin.streams.toList


@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun PetStoreScreen(pets: MutableList<Pet>, petViewModel: PetViewModel = hiltViewModel()) {
    val soldPets = petViewModel.petState.value.stream().filter { !it.isSold }.toList()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                scope.launch {
                    if (scaffoldState.bottomSheetState.isCollapsed) {
                        scaffoldState.bottomSheetState.expand()
                    } else {
                        scaffoldState.bottomSheetState.collapse()
                    }
                }
            }, content = {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            },
                containerColor = colorResource(id = R.color.copper)
            )
        },
        sheetContent = {
            BottomSheetAddPet(bottomSheetScaffoldState = scaffoldState)
        },
        sheetGesturesEnabled = true,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 32.dp,
        floatingActionButtonPosition = FabPosition.End,
        sheetElevation = 0.dp
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            contentPadding = PaddingValues(top = 8.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(soldPets.size) { index ->
                PetInfoCard(
                    pet = soldPets[index],
                    petViewModel
                )
            }
        }
    }
}
