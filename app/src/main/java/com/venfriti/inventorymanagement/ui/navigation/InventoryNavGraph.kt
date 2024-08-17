package com.venfriti.inventorymanagement.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.venfriti.inventorymanagement.ui.network.WebSocketManager
import com.venfriti.inventorymanagement.ui.network.setupWebSocket
import com.venfriti.inventorymanagement.ui.screens.HomeDestination
import com.venfriti.inventorymanagement.ui.screens.InventoryHomeScreen
import com.venfriti.inventorymanagement.ui.screens.LoginDestination
import com.venfriti.inventorymanagement.ui.screens.LoginScreen


@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var isAuthenticated by remember { mutableStateOf(false) }
    val webSocketManager = remember { WebSocketManager() }

    LaunchedEffect(Unit) {
        setupWebSocket { success ->
            isAuthenticated = success
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) HomeDestination.route else LoginDestination.route,
        modifier = modifier
    ) {
        composable(route = LoginDestination.route) {
            LoginScreen(
                webSocketManager = webSocketManager,
                onLogin = {
                    isAuthenticated = true
                    navController.navigate(HomeDestination.route) {
                        popUpTo(LoginDestination.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = HomeDestination.route) {
            InventoryHomeScreen(
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

