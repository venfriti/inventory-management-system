package com.venfriti.inventorymanagement.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.venfriti.inventorymanagement.R
import com.venfriti.inventorymanagement.ui.navigation.NavigationDestination
import com.venfriti.inventorymanagement.utils.initSocketIO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object LoginDestination : NavigationDestination {
    override val route = "login"
    override val titleRes = R.string.app_name
}

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    var receivedMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            Text(text = "Scan your card to login")
            Button(
                onClick = onLogin
            ) {
                Text(text = "Login Anyway")
            }
        }
    }
    LaunchedEffect(Unit) {
        initSocketIO { receivedText ->
            scope.launch(Dispatchers.Main) {
                receivedMessage = receivedText
                if (receivedMessage == "success"){
                    onLogin()
                }
            }
        }
    }
}