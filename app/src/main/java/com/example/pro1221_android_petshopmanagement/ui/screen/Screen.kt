package com.example.pro1221_android_petshopmanagement.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    object LoginScreen : Screen(route = "login_screen")
    object SignUpScreen : Screen(route = "sign_up_screen")


}

sealed class DrawerNavigationItem(var route: String, var icon:ImageVector, var title:String) {
    object PetStoreScreen : DrawerNavigationItem(route = "pet_store_screen", Icons.Filled.CheckCircle, "Store")
    object SoldPetScreen : DrawerNavigationItem(route = "sold_pet_screen", Icons.Filled.CheckCircle, "Sold")
    object KindOfAnimalScreen : DrawerNavigationItem(route = "kind_of_animal_screen", Icons.Filled.CheckCircle, "Kind")
    object CustomerScreen : DrawerNavigationItem(route = "customer_screen", Icons.Filled.CheckCircle, "Customer")

    // TODO: ADD PetInfoScreen.kt

    object RankingScreen : DrawerNavigationItem(route = "ranking_screen", Icons.Filled.CheckCircle, "Ranking")
}