package com.example.pro1221_android_petshopmanagement.presentation.screen.component.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import com.example.pro1221_android_petshopmanagement.domain.model.Customer

@ExperimentalMaterialApi
@Composable
fun CustomerItem(customer: Customer) {
    ListItem {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            backgroundColor = colorResource(id = R.color.copper),
            elevation = 5.dp,
            shape = RoundedCornerShape(15.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, bottom = 16.dp, end = 16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = customer.image
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp)
                            .clip(CircleShape)
                            .border(width = 2.dp, shape = CircleShape, color = Color.Gray)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = customer.name,
                            style = MaterialTheme.typography.caption,
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Địa chỉ: ${customer.address}",
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Số điện thoại: ${customer.phoneNumber}",
                    color = Color.White
                )
            }
        }
    }
}