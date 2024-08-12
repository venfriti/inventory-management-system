package com.venfriti.inventorymanagement.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.venfriti.inventorymanagement.R
import com.venfriti.inventorymanagement.data.Inventory
import com.venfriti.inventorymanagement.ui.AppViewModelProvider
import com.venfriti.inventorymanagement.ui.InventoryTopAppBar
import com.venfriti.inventorymanagement.ui.LoginDetails
import com.venfriti.inventorymanagement.ui.SearchBar
import com.venfriti.inventorymanagement.ui.navigation.NavigationDestination
import com.venfriti.inventorymanagement.ui.theme.backgroundBlue
import com.venfriti.inventorymanagement.ui.theme.componentBackground
import com.venfriti.inventorymanagement.ui.theme.dirtyWhite


object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryHomeScreen(
    viewModel: InventoryViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { InventoryTopAppBar(scrollBehavior = scrollBehavior) }
    ) { innerPadding ->
        InventoryHomeBody(viewModel, innerPadding)
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryHomeBody(viewModel: InventoryViewModel, contentPadding: PaddingValues) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .padding(contentPadding)
            .padding(horizontal = 36.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        var selectedInventory by rememberSaveable { mutableStateOf<Inventory?>(null) }

        var onOpenDialog: (Inventory) -> Unit = { inventory ->
            selectedInventory = inventory
        }

        val searchResults by viewModel.searchResults.collectAsState(initial = emptyList())
        var textInput by rememberSaveable { mutableStateOf("") }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .weight(2f)
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
            }
            Box(
                modifier = Modifier.weight(5f),
                contentAlignment = Alignment.Center
            ) {
                SearchBar(
                    searchQuery = textInput,
                    onSearch = { textInput = it },
                    onValueChange = {
                        textInput = it
                        viewModel.setSearchQuery(it)
                    },
                    onClear = {
                        textInput = ""
                        viewModel.setSearchQuery("")
                    }
                )
            }
            Box(
                modifier = Modifier.weight(3f),
                contentAlignment = Alignment.Center
            ) {
                LoginDetails(
                    name = stringResource(R.string.account_name),
                    icon = Icons.Filled.AccountCircle
                )
            }
        }


        InventoryGridList(
            searchResults,
            onOpenDialog = onOpenDialog,
        )
//        PopUpOverlay()

        selectedInventory?.let { inventory: Inventory ->
            BasicAlertDialog(
                modifier = Modifier.padding(20.dp),
                onDismissRequest = { selectedInventory = null },
                content = {
                    PopUpOverlay(
                        inventory,
                        viewModel
                    )
                }
            )
        }
    }
}

@Composable
fun PopUpOverlay(
    product: Inventory,
    viewModel: InventoryViewModel
) {
    val isAddStockClicked = remember { mutableStateOf(false) }
    val isRemoveStockClicked = remember { mutableStateOf(false) }

    var amount by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .background(dirtyWhite)
            .padding(12.dp)
            .clip(ShapeDefaults.Medium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ReusableBox(
            text = product.name,
            textSize = 24.sp,
        )
        Spacer(Modifier.height(24.dp))
        ReusableBox(
            text = stringResource(R.string.product_amount, product.amount),
            textSize = 24.sp
        )
        Spacer(Modifier.height(24.dp))
        HorizontalDivider(modifier = Modifier.fillMaxWidth(0.5f), color = Color.Black)
        Spacer(Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 36.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if(
                !isRemoveStockClicked.value
            ){
                Button(
                    onClick = {
                        isAddStockClicked.value = true
                        if (isAddStockClicked.value) {
                            if (amount == "") { }
                            else { viewModel.addStock(product, amount.toInt()) }
                        }
                              },
                    modifier = Modifier
                        .height(50.dp)
                        .weight(3f),
                    colors = ButtonColors(
                        containerColor = componentBackground,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.Gray
                    )
                ) {
                    Text(text = "Add Stock")
                }
                Spacer(modifier = Modifier.weight(1f))
            }

            if (isAddStockClicked.value || isRemoveStockClicked.value) {
                TextField(
                    value = amount,
                    onValueChange = { newValue ->
                        if (newValue.all {it.isDigit()}){
                            amount = newValue
                        }
                    },
                    modifier = Modifier
                        .height(50.dp)
                        .weight(3f),
                    placeholder = { Text(
                        text = "Enter Stock Amount",
                        fontSize = 16.sp
                    ) },
                    leadingIcon = {
                        IconButton(onClick = {
                            if (amount == "" || amount == "0"){}
                            else {
                                amount = (amount.toInt() - 1).toString()
                            }
                        }) {
                            Icon(
                                Icons.Filled.Remove,
                                contentDescription = "Remove Stock"
                            )
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            amount = if (amount == "") {
                                "1"
                            } else {
                                (amount.toInt() + 1).toString()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add Stock"
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    maxLines = 1,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = backgroundBlue,
                        unfocusedContainerColor = backgroundBlue,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                )
            }
            if (isRemoveStockClicked.value){
                Spacer(modifier = Modifier.weight(1f))
            }
            if (!isAddStockClicked.value) {
                Button(
                    onClick = {
                        isRemoveStockClicked.value = !isRemoveStockClicked.value
                    },
                    modifier = Modifier
                        .height(50.dp)
                        .weight(3f),
                    colors = ButtonColors(
                        containerColor = componentBackground,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.Gray
                    )
                ) {
                    Text(
                        text = "Remove Stock"
                    )
                }
            }
        }
    }
}

@Composable
fun ReusableBox(
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    textSize: TextUnit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            modifier = Modifier,
            style = textStyle,
            fontSize = textSize
        )
    }
}

@Composable
fun InventoryGridList(
    products: List<Inventory>,
    onOpenDialog: (Inventory) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(vertical = 16.dp),
        columns = GridCells.Fixed(2),
        contentPadding = contentPadding
    ) {
        items(items = products, key = { it.id }) { product ->
            Product(
                product,
                onOpenDialog = { onOpenDialog(product) },
                modifier = Modifier.padding(top = 0.dp, start = 8.dp, end = 8.dp, bottom = 16.dp)
            )
        }
    }
}

@Composable
fun Product(
    product: Inventory,
    onOpenDialog: (Inventory) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(ShapeDefaults.Medium)
            .background(backgroundBlue)
            .fillMaxWidth(0.5f)
            .fillMaxHeight(0.3f),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = 16.dp,
                    horizontal = 8.dp
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(3f),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.name, product.name),
                        fontSize = 24.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.amount, product.amount),
                        fontSize = 24.sp
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f)
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { onOpenDialog(product) },
                    enabled = true,
                    shape = ShapeDefaults.Large,
                    colors = ButtonColors(
                        containerColor = componentBackground,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.Gray
                    )
                ) {
                    Text(
                        text = "Add/Remove",
                        fontSize = 16.sp
                    )
                }
            }

        }

    }
}
