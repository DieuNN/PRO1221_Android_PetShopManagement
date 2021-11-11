package com.example.pro1221_android_petshopmanagement.ui.screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pro1221_android_petshopmanagement.R

@Preview(showBackground = true)
@Composable
fun BottomSheetAddCustomer() {
    Column() {
        BottomSheetHeader(title = "Header")
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            BottomSheetImage()
        }
        Column(modifier = Modifier.padding(8.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            BottomSheetInput(label = "Name")
            Spacer(modifier = Modifier.height(8.dp))
            BottomSheetInput(label = "Name")
            Spacer(modifier = Modifier.height(8.dp))
            BottomSheetInput(label = "Name")
        }
    }
}

//@Preview
@Composable
fun BottomSheetImage() {
    Box(
        Modifier
            .width(120.dp)
            .height(128.dp)
    ) {
        Image(
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.manuel_vivo),
            contentDescription = null,
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End
        ) {
            BottomSheetFAB()
        }
    }

}

//@Preview
@Composable
fun BottomSheetFAB() {
    androidx.compose.material3.FloatingActionButton(
        onClick = { /*TODO*/ },
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


@Composable
fun BottomSheetInput(label: String) {
    var inputState by remember {
        mutableStateOf("")
    }
    var errorState by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = inputState,
        onValueChange = { inputState = it },
        modifier = Modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.h6,
        label = {
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )
        },
        shape = RoundedCornerShape(32.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black.copy(.4f),
            focusedLabelColor = Color.Black,
            cursorColor = Color.Black,
            unfocusedBorderColor = Color.Black.copy(.25f)
        )
    )
}


@Composable
fun BottomSheetHeader(title: String) {
    Card(
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        elevation = 0.dp
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { /*TODO*/ }

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_close_24),
                        contentDescription = null
                    )
                }
            },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_check_24),
                        contentDescription = null
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = colorResource(id = R.color.maccaroni_and_cheese)
            )
        )
    }
}



