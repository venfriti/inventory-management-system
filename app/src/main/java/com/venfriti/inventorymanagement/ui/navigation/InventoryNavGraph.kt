package com.venfriti.inventorymanagement.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.venfriti.inventorymanagement.ui.screens.HomeDestination
import com.venfriti.inventorymanagement.ui.screens.InventoryHomeScreen
import com.venfriti.inventorymanagement.ui.screens.LoginDestination
import com.venfriti.inventorymanagement.ui.screens.LoginScreen
import com.venfriti.inventorymanagement.utils.RecordCheck.isAuthenticated
import com.venfriti.inventorymanagement.utils.sendEmailWithRetry
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun InventoryNavHost(
    windowSize: WindowWidthSizeClass,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) HomeDestination.route else LoginDestination.route,
        modifier = modifier
    ) {
        composable(route = LoginDestination.route) {
            LoginScreen(
                windowSize,
                onLogin = { name ->
                    isAuthenticated = true
                    val currentTimeMillis = System.currentTimeMillis()
                    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
                    val resultDate = Date(currentTimeMillis)
                    val formattedTime = sdf.format(resultDate)

                    scope.launch {
                        val message = "$name logged in at $formattedTime"
                        sendEmailWithRetry(
                            subject = "Store Activity",
                            body = message
                        )
                    }
                    navController.navigate(HomeDestination.createRoute(name)) {
                        popUpTo(LoginDestination.route) { inclusive = true }
                    }
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
                },
                windowSize = windowSize
            )
        }
    }
}

