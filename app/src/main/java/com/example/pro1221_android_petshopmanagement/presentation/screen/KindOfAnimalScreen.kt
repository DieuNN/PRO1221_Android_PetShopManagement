package com.example.pro1221_android_petshopmanagement.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.pro1221_android_petshopmanagement.ui.model.FakeDataReposotory
import com.example.pro1221_android_petshopmanagement.ui.model.Kind
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.KindOfAnimalItem

@ExperimentalMaterialApi
@Composable
fun KindOfAnimalScreen(kinds: MutableList<Kind>) {
    LazyColumn(
        contentPadding = PaddingValues(top = 8.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(kinds.size) { index ->
            KindOfAnimalItem(kind = kinds[index])
        }
    }
}

@ExperimentalMaterialApi
//@Preview
@Composable
fun KindOfAnimalScreenPrev() {
    KindOfAnimalScreen(kinds = FakeDataReposotory.getKinds(LocalContext.current))
}