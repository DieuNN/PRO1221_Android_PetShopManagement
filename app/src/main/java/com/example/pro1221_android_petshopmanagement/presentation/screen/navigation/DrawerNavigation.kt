package com.example.pro1221_android_petshopmanagement.presentation.screen.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pro1221_android_petshopmanagement.presentation.screen.*

@ExperimentalMaterial3Api
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DrawerNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = DrawerNavigationItem.PetStoreScreen.route
    ) {
        composable(route = DrawerNavigationItem.PetStoreScreen.route) {
            PetStoreScreen()
        }
        composable(route = DrawerNavigationItem.SoldPetScreen.route) {
            SoldPetsScreen()
        }
        composable(route = DrawerNavigationItem.KindOfAnimalScreen.route) {
            KindOfAnimalScreen()
        }
        composable(route = DrawerNavigationItem.CustomerScreen.route) {
            CustomerScreen()
        }
        composable(route = DrawerNavigationItem.PetInfoScreen.route) {
            PetInfoScreen()
        }
        composable(route = DrawerNavigationItem.RankingScreen.route) {
            PetRankingScreen()
        }
    }
}