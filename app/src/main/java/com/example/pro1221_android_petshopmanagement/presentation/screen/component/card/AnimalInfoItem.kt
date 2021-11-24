package com.example.pro1221_android_petshopmanagement.presentation.screen.component.card

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.domain.model.AnimalInfo

@ExperimentalMaterialApi
@Composable
fun AnimalInfoItem(animalInfo: AnimalInfo) {
    ListItem {
        var expandedState by remember {
            mutableStateOf(false)
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = LinearOutSlowInEasing
                    )
                ),
            shape = RoundedCornerShape(15.dp),
            elevation = 5.dp,
            backgroundColor = colorResource(id = R.color.copper),
            onClick = {
                expandedState = !expandedState
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = rememberImagePainter(
                            data = animalInfo.image
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp)
                            .clip(CircleShape)
                            .border(width = 2.dp, color = Color.Gray, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = animalInfo.name,
                            style = MaterialTheme.typography.caption,
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = animalInfo.updateTime,
                            color = Color.White,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                if (expandedState) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = rememberImagePainter(
                                data = animalInfo.image
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(162.dp)
                        )
                    }
                    Row(Modifier.fillMaxWidth()) {
                        Column {
                            Text(
                                text = "Loài: ${animalInfo.kind}",
                                color = Color.White,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = animalInfo.detail,
                                color = Color.White,
                            )
                        }
                    }
                }
            }
        }
    }
}