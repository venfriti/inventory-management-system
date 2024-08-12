package com.venfriti.inventorymanagement.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.venfriti.inventorymanagement.ui.screens.HomeDestination
import com.venfriti.inventorymanagement.ui.screens.InventoryHomeScreen
import com.venfriti.inventorymanagement.ui.screens.LoginDestination
import com.venfriti.inventorymanagement.ui.screens.LoginScreen


@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = LoginDestination.route,
        modifier = modifier
    ) {
        composable(route = LoginDestination.route) {
            LoginScreen(
                navigateToHomeScreen = { navController.navigate(HomeDestination.route)}
            )
        }
        composable(route = HomeDestination.route) {
            InventoryHomeScreen()
        }
    }
}