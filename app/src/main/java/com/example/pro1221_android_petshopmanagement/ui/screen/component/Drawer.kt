package com.example.pro1221_android_petshopmanagement.ui.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pro1221_android_petshopmanagement.R


@Preview
@Composable
fun Drawer() {
    Card(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = colorResource(id = R.color.maccaroni_and_cheese),
        shape = RoundedCornerShape(
            topEnd = 32.dp,
            bottomEnd = 32.dp
        )
    ) {
        Column(modifier = Modifier.padding(horizontal = 28.dp, vertical = 16.dp)) {
            DrawerHeader()
            Spacer(modifier = Modifier.height(32.dp))
            DrawerItemHeader(text = "Thú nuôi")
            Spacer(modifier = Modifier.height(16.dp))
            DrawerItem(title = "Thú trong cửa hàng", isSelected = true)
            DrawerItem(title = "Thú đã bán", isSelected = false)
            DrawerItem(title = "Các loài thú", isSelected = false)
            DrawerDivider()
            Spacer(modifier = Modifier.height(8.dp))
            DrawerItemHeader(text = "Khách hàng")
            Spacer(modifier = Modifier.height(8.dp))
            DrawerItem(title = "Thông tin khách hàng", isSelected = false)
            DrawerDivider()
            Spacer(modifier = Modifier.height(8.dp))
            DrawerItemHeader(text = "Thông tin thêm cho chủ cửa hàng")
            Spacer(modifier = Modifier.height(8.dp))
            DrawerItem(title = "Cách chăm sóc các loại thú", isSelected = false)
            DrawerItem(title = "Thống kê doanh thu", isSelected = false)
            DrawerItem(title = "Đăng xuất", isSelected = false)
        }
    }
}

@Composable
fun DrawerHeader() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "My Pet Shop",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}


@Composable
fun DrawerItemHeader(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    )
}


@Composable
fun DrawerItem(title: String, isSelected: Boolean) {
    val backgroundColor =
        if (isSelected) colorResource(id = R.color.copper) else colorResource(id = R.color.maccaroni_and_cheese)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(64.dp),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp),
                painter = painterResource(id = R.drawable.ic_baseline_fiber_manual_record_24),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DrawerDivider() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Divider(
            color = Color.Black.copy(alpha = .12f)
        )
    }
}



