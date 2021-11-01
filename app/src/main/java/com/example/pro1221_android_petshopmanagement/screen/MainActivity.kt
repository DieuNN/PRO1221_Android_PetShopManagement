package com.example.pro1221_android_petshopmanagement.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pro1221_android_petshopmanagement.ui.theme.PRO1221_Android_PetShopManagementTheme

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PRO1221_Android_PetShopManagementTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainContent()
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun MainContent(){
    val longtext = "Lorem Ipsum is simply dummy text of the printing" +
            " and typesetting industry. Lorem Ipsum has been the" +
            " industry's standard dummy text ever since the 1500s," +
            " when an unknown printer took a galley of type and" +
            " scrambled it to make a type specimen book."

    val isVisible = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .background(Color(0xFFEDEAE0))
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Button(
            onClick = {
                isVisible.value = !isVisible.value
            },
        ) {
            Text(if (isVisible.value)"Hide" else "Show")
        }

        AnimatedVisibility(
            visible = isVisible.value,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFD2691E))
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = longtext,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Cursive,
                    color = Color(0xFF58111A),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}


@ExperimentalAnimationApi
@Preview
@Composable
fun ComposablePreview(){
    MainContent()
}
