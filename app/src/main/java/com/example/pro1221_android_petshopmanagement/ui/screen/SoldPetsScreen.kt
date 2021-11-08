package com.example.pro1221_android_petshopmanagement.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pro1221_android_petshopmanagement.ui.model.FakeDataReposotory
import com.example.pro1221_android_petshopmanagement.ui.model.Pet
import com.example.pro1221_android_petshopmanagement.ui.screen.component.PetInfoCard
import java.util.stream.Collectors

@Composable
fun SoldPetsScreen(pets:MutableList<Pet>) {
    var soldPets = pets.stream().filter{ it.isSold }.collect(Collectors.toList())

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(soldPets.size) { index->
            PetInfoCard(pet = soldPets[index])
        }
    }
}

@Preview
@Composable
fun SoldPetsScreenPrev() {
    SoldPetsScreen(FakeDataReposotory.getPets(LocalContext.current))
}