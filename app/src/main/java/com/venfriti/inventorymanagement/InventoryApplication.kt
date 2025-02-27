package com.venfriti.inventorymanagement

import android.app.Application
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.venfriti.inventorymanagement.data.AppContainer
import com.venfriti.inventorymanagement.data.AppDataContainer
import com.venfriti.inventorymanagement.ui.navigation.InventoryNavHost


class InventoryApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}

@Composable
fun InventoryApp(
    windowSize: WindowWidthSizeClass,
    navController: NavHostController = rememberNavController()
) {
    InventoryNavHost(
        windowSize = windowSize,
        navController = navController
    )
}

