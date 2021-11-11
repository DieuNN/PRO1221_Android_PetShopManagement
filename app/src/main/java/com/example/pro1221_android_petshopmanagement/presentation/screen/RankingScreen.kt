package com.example.pro1221_android_petshopmanagement.ui.screen

import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import com.example.pro1221_android_petshopmanagement.ui.screen.component.PetRankItem
import java.util.stream.Collectors

@ExperimentalMaterialApi
@Composable
fun PetRankingScreen(pets: MutableList<Pet>) {
    var petSortedByPrice =
        pets.stream().sorted { pet1, pet2 -> -pet1.price.compareTo(pet2.price) }
            .collect(Collectors.toList())
    LazyColumn(
        contentPadding = PaddingValues(top = 8.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(petSortedByPrice.size) { index ->
            PetRankItem(pet = petSortedByPrice[index], index = index + 1)

        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun PetRankingScreenPrev() {
    val ctx = LocalContext.current
    PetRankingScreen(
        mutableListOf(
            Pet(
                name = "Doge",
                image = null,
                price = 1000,
                id = 10,
                kind = "Dog",
                detail = "This is doge detail example",
                updateTime = "20/12/2002",
                isSold = true
            ), Pet(
                name = "Doge",
                image = null,
                price = 1000,
                id = 10,
                kind = "Dog",
                detail = "This is doge detail example",
                updateTime = "20/12/2002",
                isSold = false
            ),
            Pet(
                name = "Doge",
                image = null,
                price = 1000,
                id = 10,
                kind = "Dog",
                detail = "This is doge detail example",
                updateTime = "20/12/2002",
                isSold = true
            ),
            Pet(
                name = "Doge",
                image = null,
                price = 1000,
                id = 10,
                kind = "Dog",
                detail = "This is doge detail example",
                updateTime = "20/12/2002",
                isSold = false
            ),
            Pet(
                name = "Doge",
                image = null,
                price = 1000,
                id = 10,
                kind = "Dog",
                detail = "This is doge detail example",
                updateTime = "20/12/2002",
                isSold = true
            )
        )
    )
}