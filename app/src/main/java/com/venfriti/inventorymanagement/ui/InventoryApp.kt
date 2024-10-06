package com.venfriti.inventorymanagement.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.venfriti.inventorymanagement.ui.theme.lightBlue
import com.venfriti.inventorymanagement.ui.theme.textProduct


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    onLogOutClick: () -> Unit
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Inventory Dashboard",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
        },
        actions = {
            TextButton(onClick = onLogOutClick) {
                Text(
                    text = "Log Out",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 12.dp, end = 8.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = textProduct,
            scrolledContainerColor = textProduct
        ),
        modifier = modifier
    )
}


