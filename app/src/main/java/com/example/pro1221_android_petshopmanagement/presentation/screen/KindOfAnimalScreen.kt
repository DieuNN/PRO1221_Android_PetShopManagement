package com.example.pro1221_android_petshopmanagement.presentation.screen

import android.annotation.SuppressLint
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.domain.model.Kind
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.bottom_sheet.BottomSheetAddKind
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.card.KindOfAnimalItem
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.card.ShowEmptyListWarning
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.kind.KindEvent
import com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.kind.KindViewModel
import kotlinx.coroutines.launch
import java.lang.reflect.TypeVariable
import kotlin.collections.ArrayList

@SuppressLint("UnrememberedMutableState")
@ExperimentalMaterialApi
@Composable
fun KindOfAnimalScreen(kindViewModel: KindViewModel = hiltViewModel()) {
    val kinds = mutableStateListOf<Kind>()
    kindViewModel.kindState.value.forEach { kinds.add(it) }
    val scope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(
        sheetContent = {
            Spacer(modifier = Modifier.height(32.dp))
            BottomSheetAddKind(
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                scope = scope
            )
            Spacer(modifier = Modifier.height(32.dp))
        },
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
                }, containerColor = colorResource(
                    id = R.color.copper
                )
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }

        },
        floatingActionButtonPosition = FabPosition.End,
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 32.dp,
        sheetGesturesEnabled = true,
        sheetElevation = 0.dp
    ) {
        if (kinds.isNullOrEmpty()) {
            ShowEmptyListWarning(text = "Danh sách đang trống! Thử thêm mới bằng nút ấn phía dưới!")
        } else {
            LazyColumn(
                contentPadding = PaddingValues(top = 8.dp, bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(
                    items = kinds,
                    key = { _: Int, item: Kind ->
                        item.id.hashCode()
                    }
                ) { _: Int, item: Kind ->
                    val state = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                kinds.remove(item)
                                kindViewModel.onEvent(KindEvent.DeleteKind(item))
                                scope.launch {
                                    val deleteResult =
                                        bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                                            message = "Đã xóa ${item.name}",
                                            actionLabel = "Hủy",
                                            duration = SnackbarDuration.Short
                                        )
                                    if (deleteResult == SnackbarResult.ActionPerformed) {
                                        kindViewModel.onEvent(KindEvent.RestoreKind(kind = item))
                                    }
                                }
                            }
                            true
                        }
                    )
                    SwipeToDismiss(
                        state = state,
                        background = {
                            val color = when (state.dismissDirection) {
                                DismissDirection.StartToEnd -> Color.Transparent
                                DismissDirection.EndToStart -> Color.Red
                                null -> Color.Transparent
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = color)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                        },
                        dismissContent = {
                            KindOfAnimalItem(kind = item)
                        },
                        directions = setOf(DismissDirection.EndToStart)
                    )
                }
            }
        }
    }

}

