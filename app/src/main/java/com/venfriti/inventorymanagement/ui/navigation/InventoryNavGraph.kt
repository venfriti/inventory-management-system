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
import com.venfriti.inventorymanagement.utils.RecordCheck.isAuthenticated
import com.venfriti.inventorymanagement.utils.sendEmail
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) HomeDestination.route else LoginDestination.route,
        modifier = modifier
    ) {
        composable(route = LoginDestination.route) {
            LoginScreen(
                onLogin = { name ->
                    isAuthenticated = true
                    val currentTimeMillis = System.currentTimeMillis()
                    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
                    val resultDate = Date(currentTimeMillis)
                    val formattedTime = sdf.format(resultDate)
                    navController.navigate(HomeDestination.createRoute(name)) {
                        popUpTo(LoginDestination.route) { inclusive = true }
                    }
//                    sendEmail(
//                        "ADMIN_EMAIL",
//                        "Store Activity",
//                        "$name logged in at $formattedTime"
//                    )
                }
            )
        }
        composable(route = HomeDestination.route) { navBackStackEntry ->
            val name = navBackStackEntry.arguments?.getString("name")
            InventoryHomeScreen(
                name = name,
                onLogout = {
                    isAuthenticated = false
                    navController.navigate(LoginDestination.route) {
                        popUpTo(HomeDestination.route) { inclusive = true }
                    }
                }
            )
        }
    }
}

