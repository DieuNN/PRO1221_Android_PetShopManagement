package com.example.pro1221_android_petshopmanagement.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pro1221_android_petshopmanagement.ui.model.FakeDataReposotory
import com.example.pro1221_android_petshopmanagement.domain.model.Kind
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.KindOfAnimalItem
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.kind.KindViewModel

@ExperimentalMaterialApi
@Composable
fun KindOfAnimalScreen(kindViewModel: KindViewModel = hiltViewModel()) {
    var kinds = kindViewModel.kindState.value
    val scope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(
        sheetContent = {// TODO: 11/16/21 Latter
        },
        floatingActionButton = {
            // TODO: 11/16/21 Latter

        },
        floatingActionButtonPosition = FabPosition.End,
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 16.dp,
        sheetGesturesEnabled = true
    ) {

    }

}

