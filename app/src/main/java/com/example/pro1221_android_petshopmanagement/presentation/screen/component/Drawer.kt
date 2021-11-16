package com.example.pro1221_android_petshopmanagement.ui.screen.component

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.ui.screen.DrawerNavigationItem
import com.example.pro1221_android_petshopmanagement.ui.screen.Screen
import kotlinx.coroutines.launch


@Composable
fun Drawer(scaffoldState: ScaffoldState, navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Card(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = colorResource(id = R.color.maccaroni_and_cheese),

        ) {
        Column(modifier = Modifier.padding(horizontal = 28.dp, vertical = 16.dp)) {
            DrawerHeader()
            Spacer(modifier = Modifier.height(32.dp))
            DrawerItemHeader(text = "Thú nuôi")
            Spacer(modifier = Modifier.height(16.dp))
            DrawerItem(
                title = "Thú trong cửa hàng",
                isSelected = currentRoute == DrawerNavigationItem.PetStoreScreen.route,
                scaffoldState = scaffoldState,
                drawerNavigation = DrawerNavigationItem.PetStoreScreen,
                onItemClick = {
                    navController.navigate(DrawerNavigationItem.PetStoreScreen.route) {
                        popUpTo(0)
                    }
                }
            )
            DrawerItem(
                title = "Thú đã bán",
                isSelected = currentRoute == DrawerNavigationItem.SoldPetScreen.route,
                scaffoldState = scaffoldState,
                drawerNavigation = DrawerNavigationItem.SoldPetScreen,
                onItemClick = {
                    navController.navigate(route = DrawerNavigationItem.SoldPetScreen.route) {
                        popUpTo(0)
                    }
                }
            )
            DrawerItem(
                title = "Các loài thú",
                isSelected = currentRoute == DrawerNavigationItem.KindOfAnimalScreen.route,
                scaffoldState = scaffoldState,
                drawerNavigation = DrawerNavigationItem.KindOfAnimalScreen,
                onItemClick = {
                    navController.navigate(route = DrawerNavigationItem.KindOfAnimalScreen.route) {
                        popUpTo(0)
                    }
                }
            )
            DrawerDivider()
            Spacer(modifier = Modifier.height(8.dp))
            DrawerItemHeader(text = "Khách hàng")
            Spacer(modifier = Modifier.height(8.dp))
            DrawerItem(
                title = "Thông tin khách hàng",
                isSelected = currentRoute == DrawerNavigationItem.CustomerScreen.route,
                scaffoldState = scaffoldState,
                drawerNavigation = DrawerNavigationItem.CustomerScreen,
                onItemClick = {
                    navController.navigate(route = DrawerNavigationItem.CustomerScreen.route) {
                        popUpTo(0)
                    }
                }

            )
            Spacer(modifier = Modifier.height(8.dp))
            DrawerDivider()
            Spacer(modifier = Modifier.height(8.dp))
            DrawerItemHeader(text = "Thông tin thêm cho chủ cửa hàng")
            Spacer(modifier = Modifier.height(8.dp))
            DrawerItem(
                title = "Cách chăm sóc các loại thú",
                // FIXME: idk
                isSelected = false,
                scaffoldState = scaffoldState,
                // TODO: Add this
                drawerNavigation = DrawerNavigationItem.SoldPetScreen,
                // TODO: Change this
                onItemClick = {
                    navController.navigate(route = DrawerNavigationItem.SoldPetScreen.route) {
                        popUpTo(0)
                    }
                }
            )
            DrawerItem(
                title = "Thống kê doanh thu",
                isSelected = currentRoute == DrawerNavigationItem.RankingScreen.route,
                scaffoldState = scaffoldState,
                drawerNavigation = DrawerNavigationItem.RankingScreen,
                onItemClick = {
                    navController.navigate(route = DrawerNavigationItem.RankingScreen.route) {
                        popUpTo(0)
                    }
                }
            )
            DrawerItem(
                title = "Đăng xuất",
                isSelected = false,
                scaffoldState = scaffoldState,
                // TODO: Add this,
                drawerNavigation = DrawerNavigationItem.SoldPetScreen,
                // TODO: Change this
                onItemClick = {
                    navController.navigate(route = DrawerNavigationItem.SoldPetScreen.route) {
                        popUpTo(0)
                    }
                }
            )
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DrawerItem(
    title: String,
    isSelected: Boolean,
    scaffoldState: ScaffoldState,
    drawerNavigation: DrawerNavigationItem,
    onItemClick: (DrawerNavigationItem) -> Unit
) {
    var backgroundColor =
        if (isSelected) colorResource(id = R.color.copper) else colorResource(id = R.color.maccaroni_and_cheese)
    val scope = rememberCoroutineScope()
    val test = rememberNavController()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(64.dp),
        elevation = 0.dp,
        onClick = {
            onItemClick(drawerNavigation)
            scope.launch {
                scaffoldState.drawerState.close()
            }
        }
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



