package com.example.pro1221_android_petshopmanagement.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.PetInfoCard
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet.PetViewModel
import kotlin.streams.toList

@ExperimentalMaterialApi
@Composable
fun SoldPetsScreen(petViewModel: PetViewModel = hiltViewModel()) {
    var soldPets = petViewModel.petState.value.stream().filter { it.isSold }.toList()
    val scaffoldState = rememberScaffoldState()
    Scaffold (
        scaffoldState = scaffoldState
            ) {
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            contentPadding = PaddingValues(top = 8.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(soldPets.size) { index ->
                PetInfoCard(
                    pet = soldPets[index],
                    viewModel = petViewModel,
                    scaffoldState = scaffoldState
                )
            }
        }
    }
}
