package com.example.pro1221_android_petshopmanagement.presentation.screen.component

import android.app.Activity
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.data.data_source.firebase.CommonData
import com.example.pro1221_android_petshopmanagement.data.data_source.firebase.UserData
import com.example.pro1221_android_petshopmanagement.data.data_source.firebase.getGoogleSignInConfigure
import com.example.pro1221_android_petshopmanagement.presentation.screen.DrawerNavigationItem
import com.example.pro1221_android_petshopmanagement.presentation.screen.component.card.ProgressDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@DelicateCoroutinesApi
@Composable
fun Drawer(scaffoldState: ScaffoldState, navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val context = LocalContext.current as Activity
    val isConfirmExitDialogShowing = remember {
        mutableStateOf(false)
    }
    val isSyncingDialogShowing = remember {
        mutableStateOf(false)
    }

    if (isSyncingDialogShowing.value) {
        ProgressDialog {
            isSyncingDialogShowing.value = false

        }
    }

    // Confirm Exit dialog
    if (isConfirmExitDialogShowing.value) {
        androidx.compose.material3.AlertDialog(
            title = { Text(text = "X??c nh???n tho??t", fontSize = 24.sp) },
            text = { Text(text = "X??c nh???n ?????ng b??? d??? li???u v?? tho??t?") },
            onDismissRequest = {
                isConfirmExitDialogShowing.value = false
            },
            confirmButton = {
                TextButton(onClick = {
                    val mAuth = FirebaseAuth.getInstance()
//                    // clear local data before exit
                    isSyncingDialogShowing.value = true
                    GlobalScope.launch {
                        CommonData(context = context).apply {
                            syncWhenLogout()
                        }
                        UserData(
                            context = context,
                            showProcessDialog = {
                                isSyncingDialogShowing.value = false
                            },
                            currentUserUid = mAuth.currentUser!!.uid,
                        ).apply {
                            syncWhenLogout()
                        }
                        android.os.Handler(Looper.getMainLooper()).postDelayed({
                            mAuth.signOut()
                            getGoogleSignInConfigure(context = context).signOut()
                            context.finishAffinity()
                        }, 5000)
                    }
                }) {
                    Text(text = "Tho??t", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { isConfirmExitDialogShowing.value = false }) {
                    Text(text = "H???y", color = Color.Black)
                }
            },
            containerColor = colorResource(id = R.color.maccaroni_and_cheese)
        )
    }

    Card(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = colorResource(id = R.color.maccaroni_and_cheese),

        ) {
        Column(modifier = Modifier.padding(horizontal = 28.dp, vertical = 16.dp)) {
            DrawerHeader()
            Spacer(modifier = Modifier.height(32.dp))
            DrawerItemHeader(text = "Th?? nu??i")
            Spacer(modifier = Modifier.height(16.dp))
            DrawerItem(
                title = "Th?? trong c???a h??ng",
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
                title = "Th?? ???? b??n",
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
                title = "C??c lo??i th??",
                isSelected = currentRoute == DrawerNavigationItem.KindOfAnimalScreen.route,
                scaffoldState = scaffoldState,
                drawerNavigation = DrawerNavigationItem.KindOfAnimalScreen,
                onItemClick = {
                    navController.navigate(route = DrawerNavigationItem.KindOfAnimalScreen.route) {
                        popUpTo(0)
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            DrawerDivider()
            Spacer(modifier = Modifier.height(8.dp))
            DrawerItemHeader(text = "Kh??ch h??ng")
            Spacer(modifier = Modifier.height(8.dp))
            DrawerItem(
                title = "Th??ng tin kh??ch h??ng",
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
            DrawerItemHeader(text = "Th??ng tin th??m cho ch??? c???a h??ng")
            Spacer(modifier = Modifier.height(8.dp))
            DrawerItem(
                title = "C??ch ch??m s??c c??c lo???i th??",
                // FIXME: add this
                isSelected = currentRoute == DrawerNavigationItem.AnimalInfoScreen.route,
                scaffoldState = scaffoldState,
                drawerNavigation = DrawerNavigationItem.SoldPetScreen,
                onItemClick = {
                    navController.navigate(route = DrawerNavigationItem.AnimalInfoScreen.route) {
                        popUpTo(0)
                    }
                }
            )
            DrawerItem(
                title = "Th???ng k?? doanh thu",
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
                title = "????ng xu???t",
                isSelected = false,
                scaffoldState = scaffoldState,
                drawerNavigation = DrawerNavigationItem.CustomerScreen,
                onItemClick = { isConfirmExitDialogShowing.value = true }
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
    onItemClick: (DrawerNavigationItem) -> Unit,
) {
    var backgroundColor =
        if (isSelected) colorResource(id = R.color.copper) else colorResource(id = R.color.maccaroni_and_cheese)
    val scope = rememberCoroutineScope()
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
        },
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



