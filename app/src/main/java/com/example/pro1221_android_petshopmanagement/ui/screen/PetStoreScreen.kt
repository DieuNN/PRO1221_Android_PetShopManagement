package com.example.pro1221_android_petshopmanagement.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pro1221_android_petshopmanagement.ui.model.FakeDataReposotory
import com.example.pro1221_android_petshopmanagement.ui.model.Pet
import com.example.pro1221_android_petshopmanagement.ui.screen.component.PetInfoCard
import java.util.stream.Collectors


@Composable
fun PetStoreScreen(pets: MutableList<Pet>) {
    var soldPets = pets.stream().filter{!it.isSold}.collect(Collectors.toList())
    Column {
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(soldPets.size) { index ->
                PetInfoCard(pet = soldPets[index])
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PetStoreScreenPrev() {
    PetStoreScreen(FakeDataReposotory.getPets(LocalContext.current))
}