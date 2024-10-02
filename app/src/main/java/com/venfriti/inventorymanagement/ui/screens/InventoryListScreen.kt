package com.venfriti.inventorymanagement.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
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
import kotlinx.coroutines.delay


object HomeDestination : NavigationDestination {
    override val route = "home/{name}"
    override val titleRes = R.string.app_name
    fun createRoute(name: String) = "home/$name"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryHomeScreen(
    name: String?,
    onLogout: () -> Unit,
    viewModel: InventoryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var addInventoryPrompt by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            InventoryTopAppBar(
                scrollBehavior = scrollBehavior,
                onLogOutClick = onLogout
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addInventoryPrompt = true },
                shape = MaterialTheme.shapes.extraLarge,
                containerColor = backgroundBlue,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Inventory",
                )
            }
        },
    ) { innerPadding ->
        InventoryHomeBody(name, onLogout, viewModel, innerPadding)
        if (addInventoryPrompt){
            BasicAlertDialog(
                modifier = Modifier.padding(20.dp),
                onDismissRequest = { addInventoryPrompt = false },
                content = {
                    AddInventoryDialog(
                        viewModel,
                        lastInteractionTime = 0,
                        onClose = { addInventoryPrompt = false },
                        resetTimer = {}
                    )
                }
            )
        }
    }
}

@Composable
fun AddInventoryDialog(
    viewModel: InventoryViewModel,
    lastInteractionTime: Long,
    onClose: () -> Unit,
    resetTimer: () -> Unit,
) {
    val context = LocalContext.current
    var amount by remember { mutableStateOf("") }
    var productName by remember { mutableStateOf("") }

    if (lastInteractionTime > 180000) {
        onClose()
    }

    Column(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .background(dirtyWhite)
            .padding(12.dp)
            .clip(ShapeDefaults.Medium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Box(
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Product:",
                    fontSize = 24.sp,
                    modifier = Modifier,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            TextField(
                value = productName,
                onValueChange = {
                    productName = it
                },
                modifier = Modifier,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                textStyle = TextStyle(
                    fontSize = 16.sp
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
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(Modifier.height(24.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Box(
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Amount:",
                    fontSize = 24.sp,
                    modifier = Modifier,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            TextField(
                value = amount,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        amount = newValue
                    }
                },
                modifier = Modifier,
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
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
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(Modifier.height(24.dp))
        HorizontalDivider(modifier = Modifier.fillMaxWidth(0.5f), color = Color.Black)
        Spacer(Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 36.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    resetTimer()
                    if (amount == "" || productName == "") {
                            Toast.makeText(
                                context,
                                "Enter Product Name and Amount",
                                Toast.LENGTH_LONG
                            ).show()
                    } else {
                        viewModel.addInventory(productName, amount.toInt())
                        onClose()
                        Toast.makeText(
                            context,
                            "Inventory Added",
                            Toast.LENGTH_LONG
                        ).show()
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryHomeBody(
    name: String?,
    onLogout: () -> Unit,
    viewModel: InventoryViewModel,
    contentPadding: PaddingValues
) {
    val focusManager = LocalFocusManager.current
    var lastInteractionTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

    fun resetTimer() {
        lastInteractionTime = System.currentTimeMillis()
    }

    var currentTime by remember { mutableLongStateOf(System.currentTimeMillis()) }
    currentTime = System.currentTimeMillis()
    var difference by remember { mutableLongStateOf(0L) }
    difference = currentTime - lastInteractionTime

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000) // Check every second
            if (currentTime - lastInteractionTime > 300000) { // 5 minutes timeout
                onLogout()
            }
        }
    }

    Column(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                    resetTimer()
                })
            }
            .padding(contentPadding)
            .padding(horizontal = 36.dp)
    ) {
        var selectedInventory by rememberSaveable { mutableStateOf<Inventory?>(null) }
        val searchResults by viewModel.searchResults.collectAsState(initial = emptyList())
        var textInput by rememberSaveable { mutableStateOf("") }

        val onOpenDialog: (Inventory) -> Unit = { inventory ->
            selectedInventory = inventory
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .weight(2f)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = viewModel::addInventoryList,
                    modifier = Modifier,
                    colors = ButtonColors(
                        containerColor = componentBackground,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.Gray
                    )
                ) {
                    Text(text = "populate")
                }
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
                        resetTimer()
                    },
                    onClear = {
                        textInput = ""
                        viewModel.setSearchQuery("")
                        resetTimer()
                    }
                )
            }
            Box(
                modifier = Modifier.weight(3f),
                contentAlignment = Alignment.Center,
            ) {
                LoginDetails(
                    name = name ?: "No Account",
                    icon = Icons.Filled.AccountCircle
                )
            }
        }

        InventoryGridList(
            searchResults,
            onOpenDialog = onOpenDialog,
            resetTimer = { resetTimer() }
        )

        selectedInventory?.let { inventory: Inventory ->
            BasicAlertDialog(
                modifier = Modifier.padding(20.dp),
                onDismissRequest = { selectedInventory = null },
                content = {
                    PopUpOverlay(
                        inventory,
                        viewModel,
                        difference,
                        onClose = { selectedInventory = null },
                        resetTimer = { resetTimer() }
                    )
                }
            )
        }
    }
}

@Composable
fun PopUpOverlay(
    product: Inventory,
    viewModel: InventoryViewModel,
    lastInteractionTime: Long,
    onClose: () -> Unit,
    resetTimer: () -> Unit,
) {
    val context = LocalContext.current
    val isAddStockClicked = remember { mutableStateOf(false) }
    val isRemoveStockClicked = remember { mutableStateOf(false) }
    var amount by remember { mutableStateOf("") }

    if (lastInteractionTime > 90000) {
        onClose()
    }

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
        HorizontalDivider(modifier = Modifier.fillMaxWidth(0.6f), color = Color.Black)
        Spacer(Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 36.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (
                !isRemoveStockClicked.value
            ) {
                Button(
                    onClick = {
                        if (isAddStockClicked.value) {
                            if (amount == "" && isAddStockClicked.value) {
                                Toast.makeText(
                                    context,
                                    "Enter Stock amount",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                viewModel.addStock(product, amount.toInt())
                                onClose()
                                Toast.makeText(
                                    context,
                                    if (amount == "1"){"Item added"}
                                    else {"$amount ${product.name} added"},
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        isAddStockClicked.value = true
                        resetTimer()
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
                        if (newValue.all { it.isDigit() }) {
                            amount = newValue
                        }
                    },
                    modifier = Modifier
                        .height(50.dp)
                        .weight(3f),
                    placeholder = {
                        Text(
                            text = "Enter Stock Amount",
                            fontSize = 16.sp
                        )
                    },
                    leadingIcon = {
                        IconButton(onClick = {
                            if (amount != "" && amount != "0") {
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
            if (isRemoveStockClicked.value) {
                Spacer(modifier = Modifier.weight(1f))
            }
            if (!isAddStockClicked.value) {
                Button(
                    onClick = {
                        isRemoveStockClicked.value = true
                        if (isRemoveStockClicked.value) {
                            when {
                                amount == ""  -> {
                                    Toast.makeText(
                                        context,
                                        "Enter Stock Amount",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                                amount.toInt() > product.amount -> {
                                    Toast.makeText(
                                        context,
                                        "Not enough stock",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                                else -> {
                                    viewModel.removeStock(product, amount.toInt())
                                    onClose()
                                    Toast.makeText(
                                        context,
                                        if (amount == "1"){"Item removed"}
                                        else {"$amount ${product.name} removed"},
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                        isRemoveStockClicked.value = true
                        resetTimer()
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
    contentPadding: PaddingValues = PaddingValues(0.dp),
    resetTimer: () -> Unit
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
                modifier = Modifier.padding(top = 0.dp, start = 8.dp, end = 8.dp, bottom = 16.dp),
                resetTimer = resetTimer
            )
        }
    }
}

@Composable
fun Product(
    product: Inventory,
    onOpenDialog: (Inventory) -> Unit,
    modifier: Modifier = Modifier,
    resetTimer: () -> Unit
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
                    onClick = {
                        onOpenDialog(product)
                        resetTimer()
                    },
                    enabled = true,
                    shape = ShapeDefaults.Medium,
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
