package com.example.pro1221_android_petshopmanagement.presentation.screen.component.bottom_sheet

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pro1221_android_petshopmanagement.R

@Composable
fun FABPickImage(onImagePick: () -> Unit) {
    androidx.compose.material3.FloatingActionButton(
        onClick = onImagePick,
        containerColor = colorResource(id = R.color.maccaroni_and_cheese),
        modifier = Modifier
            .width(44.dp)
            .height(44.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_outline_edit_24),
            contentDescription = null
        )
    }
}