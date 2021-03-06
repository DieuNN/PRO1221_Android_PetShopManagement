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
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.bottom_sheet.BottomSheetAddAnimalInfo
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.card.AnimalInfoItem
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.card.ShowEmptyListWarning
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.animal.AnimalInfoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimalInfoScreen(animalInfoViewModel: AnimalInfoViewModel = hiltViewModel()) {
    val animalsInfo = animalInfoViewModel.animalState.value
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetContent = {
            Spacer(modifier = Modifier.height(32.dp))
            BottomSheetAddAnimalInfo(
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                scope = scope
            )
            Spacer(modifier = Modifier.height(32.dp))
        },
        scaffoldState = bottomSheetScaffoldState,
        floatingActionButtonPosition = FabPosition.End,
        sheetPeekHeight = 32.dp,
        sheetGesturesEnabled = true,
        sheetElevation = 0.dp,
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
    )
    {
        if (animalsInfo.isEmpty()) {
            ShowEmptyListWarning(text = "Danh s??ch ??ang tr???ng! Th??? th??m m???i b???ng n??t ???n ph??a d?????i!")
        } else {
            LazyColumn(
                contentPadding = PaddingValues(top = 8.dp, bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(animalsInfo.size) { index: Int ->
                    AnimalInfoItem(animalInfo = animalsInfo[index])
                }
            }
        }
    }
}