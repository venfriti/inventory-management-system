package com.venfriti.inventorymanagement

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.venfriti.inventorymanagement.ui.theme.InventoryManagementTheme
import com.venfriti.inventorymanagement.utils.RecordCheck.isAuthenticated

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContent {
            InventoryManagementTheme {
                InventoryApp()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        isAuthenticated = false
    }
}
