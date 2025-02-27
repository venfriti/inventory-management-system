package com.venfriti.inventorymanagement.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.venfriti.inventorymanagement.R
import com.venfriti.inventorymanagement.ui.navigation.NavigationDestination
import com.venfriti.inventorymanagement.utils.initWebSocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object LoginDestination : NavigationDestination {
    override val route = "login"
    override val titleRes = R.string.app_name
}

@Composable
fun LoginScreen(
    windowSize: WindowWidthSizeClass,
    onLogin: (String) -> Unit
) {
    var receivedMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()


    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            CompactBackground( onLogin )
        }
        else -> {
            ExpandedBackground( onLogin )
        }
    }

    LaunchedEffect(Unit) {
        initWebSocket { name, receivedText ->
            scope.launch(Dispatchers.Main) {
                receivedMessage = receivedText
                if (receivedMessage == "success"){
                    onLogin(name)
                }
            }
        }
    }
}

@Composable
fun CompactBackground(
    onLogin: (String) -> Unit
){
    Box(modifier = Modifier.fillMaxSize().padding(horizontal = 6.dp)) {
        Image(
            painter = painterResource(id = R.drawable.splash_screen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomEnd,
        ) {
            Column {
                Button(
                    onClick = { onLogin("Test") },
                    colors = ButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Black
                    )
                ) {
                }
            }
        }
    }
}

@Composable
fun ExpandedBackground(
    onLogin: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.splash_screen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(80.dp),
            contentAlignment = Alignment.BottomEnd,
        ) {
            Column {
                Button(
                    onClick = { onLogin("Test") },
                    colors = ButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Black
                    )
                ) {
                }
            }
        }
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240", showSystemUi = true)
@Composable
fun ExpandedBackgroundPreview() {
    ExpandedBackground {}
}

@Preview(device = "spec:width=411dp,height=891dp", showSystemUi = true)
@Composable
fun CompactBackgroundPreview(){
    CompactBackground {}
}