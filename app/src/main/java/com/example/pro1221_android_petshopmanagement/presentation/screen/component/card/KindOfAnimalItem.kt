package com.example.pro1221_android_petshopmanagement.presentation.screen.component.card

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.domain.model.Kind

@ExperimentalMaterialApi
@Composable
fun KindOfAnimalItem(kind: Kind) {
    var extended by remember {
        mutableStateOf(false)
    }
    ListItem {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = FastOutSlowInEasing
                    )
                ),
            backgroundColor = colorResource(id = R.color.copper),
            shape = RoundedCornerShape(15.dp),
            onClick = { extended = !extended }
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(80.dp)
                        .padding(16.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = kind.image
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp)
                            .clip(CircleShape)
                            .border(width = 2.dp, shape = CircleShape, color = Color.Gray)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = kind.name, fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
                if (extended) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = "Ghi chú: ${kind.description}", color = Color.White)
                    }
                }
            }
        }
    }
}
