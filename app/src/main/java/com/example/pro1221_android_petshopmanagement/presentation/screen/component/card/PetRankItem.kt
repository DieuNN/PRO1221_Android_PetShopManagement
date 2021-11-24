package com.example.pro1221_android_petshopmanagement.presentation.screen.component.card

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.common.collections.parseLongTimeToString
import com.example.pro1221_android_petshopmanagement.domain.model.Pet

@ExperimentalMaterialApi
@Composable
fun PetRankItem(pet: Pet, index: Int) {
    ListItem {
        var expanded by remember {
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
            backgroundColor = colorResource(id = R.color.copper),
            onClick = {
                expanded = !expanded
            },
            shape = RoundedCornerShape(15.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Card(
                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp),
                        shape = CircleShape,
                        backgroundColor = colorResource(id = R.color.maccaroni_and_cheese)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = index.toString(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = pet.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )

                }
                Spacer(modifier = Modifier.height(8.dp))
                if (expanded) {
                    Column {
                        Image(
                            painter = rememberImagePainter(data = pet.image),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Giá bán: ${pet.price}",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Ngày bán: ${parseLongTimeToString(pet.updateTime.toLong())}",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
