package com.example.pro1221_android_petshopmanagement.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.PetRankItem
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.ShowEmptyListWarning
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet.PetViewModel
import java.util.stream.Collectors

@ExperimentalMaterialApi
@Composable
fun PetRankingScreen(petViewModel: PetViewModel = hiltViewModel()) {
    val pets = petViewModel.petState.value
    val petSortedByPrice =
        pets.stream().sorted { pet1, pet2 -> -pet1.price.compareTo(pet2.price) }
            .collect(Collectors.toList())
    if (petSortedByPrice.isEmpty()) {
        ShowEmptyListWarning(text ="Chưa có thú nào ở đây để thống kê! Thử thêm một vài thú xem!")
    } else {
        LazyColumn(
            contentPadding = PaddingValues(top = 8.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(petSortedByPrice.size) { index ->
                PetRankItem(pet = petSortedByPrice[index], index = index + 1)
            }
        }
    }
}

