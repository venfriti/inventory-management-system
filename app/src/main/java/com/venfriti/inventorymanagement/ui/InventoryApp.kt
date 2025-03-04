package com.venfriti.inventorymanagement.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.venfriti.inventorymanagement.R
import com.venfriti.inventorymanagement.data.Inventory
import com.venfriti.inventorymanagement.ui.screens.CompactInventoryGridList
import com.venfriti.inventorymanagement.ui.screens.ExpandedInventoryGridList
import com.venfriti.inventorymanagement.ui.screens.InventoryGridList
import com.venfriti.inventorymanagement.ui.theme.textProduct


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    modifier: Modifier = Modifier,
    onLogOutClick: () -> Unit,
    windowSize: WindowWidthSizeClass
) {
    when (windowSize){
        WindowWidthSizeClass.Compact -> {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        text = stringResource(R.string.dashboard),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        modifier = Modifier.padding(top = 13.dp, start = 8.dp)
                    )
                },
                actions = {
                    TextButton(onClick = onLogOutClick) {
                        Text(
                            text = stringResource(R.string.log_out),
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
        else -> {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        text = stringResource(R.string.inventory_dashboard),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        modifier = Modifier.padding(start = 24.dp)
                    )
                },
                actions = {
                    TextButton(onClick = onLogOutClick) {
                        Text(
                            text = stringResource(R.string.log_out),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(top = 12.dp, end = 24.dp)
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
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true,
    device = "spec:width=1280dp,height=800dp,dpi=240"
)
@Composable
fun ExpandedInventoryHomePreview(){
    InventoryTopAppBar(
        onLogOutClick = { },
        windowSize = WindowWidthSizeClass.Expanded
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
fun CompactInventoryHomePreview(){
    InventoryTopAppBar(
        onLogOutClick = { },
        windowSize = WindowWidthSizeClass.Compact
    )
}