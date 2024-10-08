package com.venfriti.inventorymanagement

import android.app.ActivityManager
import android.content.Context
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
        startLockTaskMode()
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

//    // Optionally, override the back button press to prevent the user from exiting
//    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
//    @SuppressLint("MissingSuperCall")
//    override fun onBackPressed() {
//        // Do nothing to prevent back button exit
//    }



}
