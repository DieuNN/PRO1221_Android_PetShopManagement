package com.example.pro1221_android_petshopmanagement.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.card.PetInfoCard
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.card.ShowEmptyListWarning
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.customer.CustomerViewModel
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet.PetEvent
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet.PetViewModel
import kotlinx.coroutines.launch
import kotlin.streams.toList

@SuppressLint("UnrememberedMutableState")
@ExperimentalMaterialApi
@Composable
fun SoldPetsScreen(
    petViewModel: PetViewModel = hiltViewModel(),
    customerViewModel: CustomerViewModel = hiltViewModel()
) {
    val soldPets = mutableStateListOf<Pet>()
    petViewModel.petState.value.stream().filter { it.isSold }.toList().forEach { soldPets.add(it) }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        if (soldPets.isEmpty()) {
            ShowEmptyListWarning(text = "Danh sách đang trống!")
        } else {
            LazyColumn(
                contentPadding = PaddingValues(top = 8.dp, bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(
                    items = soldPets,
                    key = {_:Int, item:Pet ->
                        item.id.hashCode()
                    }
                ) { _: Int, item: Pet ->
                    PetInfoCard(pet = item, petViewModel = petViewModel, customerViewModel = customerViewModel, scope = scope, onDelete = {
                        scope.launch {
                            petViewModel.onEvent(PetEvent.DeletePet(pet = item))
                            val deleteResult = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Đã xóa ${item.name}",
                                actionLabel = "Hủy",
                                duration = SnackbarDuration.Short
                            )
                            if (deleteResult == SnackbarResult.ActionPerformed) {
                                petViewModel.onEvent(PetEvent.RestorePet(pet = item))
                            }
                        }
                    })
                }
            }
        }
    }
}
