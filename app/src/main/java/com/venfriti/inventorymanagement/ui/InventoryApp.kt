package com.venfriti.inventorymanagement.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.venfriti.inventorymanagement.ui.theme.lightBlue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier){
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Inventory Dashboard",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = lightBlue,
            scrolledContainerColor = lightBlue
        ),
        modifier = modifier
    )
}


