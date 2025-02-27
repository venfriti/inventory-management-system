package com.venfriti.inventorymanagement

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.google.android.gms.security.ProviderInstaller
import com.venfriti.inventorymanagement.ui.theme.InventoryManagementTheme
import com.venfriti.inventorymanagement.utils.RecordCheck.isAuthenticated
import javax.net.ssl.SSLContext

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InventoryManagementTheme {
                val windowSize = calculateWindowSizeClass(this)
                InventoryApp(
                    windowSize = windowSize.widthSizeClass
                )
            }
        }
        installTlsProvider()
    }

    override fun onPause() {
        super.onPause()
        isAuthenticated = false
    }

    private fun startLockTaskMode() {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (activityManager.lockTaskModeState == ActivityManager.LOCK_TASK_MODE_NONE) {
            startLockTask()
        }
    }

    private fun installTlsProvider() {
        try {
            ProviderInstaller.installIfNeeded(applicationContext)
            val sslContext = SSLContext.getInstance("TLSv1.2")
            sslContext.init(null, null, null)
            sslContext.createSSLEngine()
            println("TLS 1.2 support is enabled.")
        } catch (e: Exception) {
            // Handle error in case the provider installation fails
            e.printStackTrace()
            println("Failed to install TLS provider.")
        }
    }
}
