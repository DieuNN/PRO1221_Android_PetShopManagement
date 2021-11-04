package com.example.pro1221_android_petshopmanagement.ui.screen.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.ui.model.AnimalInfo
import com.example.pro1221_android_petshopmanagement.ui.theme.Shapes

@Composable
fun AppBar(title: String, rightIcon: ImageVector) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                androidx.compose.material.Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = rightIcon, contentDescription = null)
            }
        })
}

@ExperimentalMaterialApi
@Composable
fun AnimalInfoItem(animalInfo: AnimalInfo) {
    ListItem(modifier = Modifier.padding(16.dp)) {
        var expanedState by remember {
            mutableStateOf(true)
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            shape = Shapes.medium,
            elevation = 16.dp,
            border = BorderStroke(1.dp, Color.Black),
            backgroundColor = colorResource(id = R.color.copper),
            onClick = {
                expanedState = !expanedState
            }
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Text 1")
                if (expanedState) {
                    Text(text = "Text 2")
                }
            }
        }
    }
}


@Preview(name = "app-bar")
@Composable
fun AppBarPrev() {
    AppBar(title = "Title", rightIcon = Icons.Filled.Person)
}

@ExperimentalMaterialApi
@Preview(name = "animal-info-card")
@Composable
fun AnimalInfoItemPrev() {
    AnimalInfoItem(animalInfo = AnimalInfo())
}



