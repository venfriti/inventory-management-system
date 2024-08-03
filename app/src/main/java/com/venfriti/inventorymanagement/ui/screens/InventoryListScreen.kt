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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.venfriti.inventorymanagement.R
import com.venfriti.inventorymanagement.data.Inventory
import com.venfriti.inventorymanagement.ui.theme.InventoryManagementTheme
import com.venfriti.inventorymanagement.ui.theme.backgroundBlue
import com.venfriti.inventorymanagement.ui.theme.componentBackground


@Composable
fun InventoryHomeScreen(viewModel: InventoryViewModel, contentPadding: PaddingValues) {
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

        val searchQuery by viewModel.searchQuery.collectAsState()
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
            ){
            }
            Box(
                modifier = Modifier.weight(5f),
                contentAlignment = Alignment.Center
            ){
                SearchBar(
                    searchQuery = textInput,
                    onSearch = { textInput = it },
                    onValueChange = {
                        textInput = it
                        viewModel.setSearchQuery(it)
                    },
                    onClear = {
                        textInput = ""
                    }
                )
            }
            Box(
                modifier = Modifier.weight(3f),
                contentAlignment = Alignment.Center
            ){
                LoginDetails(name = "tolulope", icon = Icons.Filled.AccountCircle)
            }
        }
        InventoryGridList(
            searchResults
        )

    }
}

@Composable
fun LoginDetails(name: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ){
        Text(text = name, fontSize = 18.sp, modifier = Modifier.padding(vertical = 12.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = icon,
            contentDescription = "Profile Picture",
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onSearch: (String) -> Unit,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit
) {

    val hint = "Search..."
    var text by rememberSaveable { mutableStateOf("") }


    TextField(
        value = searchQuery,
        onValueChange = {
            text = it
            onValueChange(text)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(55.dp)
        ,
        placeholder = { Text(text = hint) },
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = backgroundBlue,
            unfocusedContainerColor = backgroundBlue,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(text)
            }
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "SearchIcon"
            )
        },
        trailingIcon = {
            IconButton(onClick = onClear) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear Search"
                )
            }
        },
        shape = ShapeDefaults.ExtraLarge
    )
}

@Composable
fun InventoryGridList(
    products: List<Inventory>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(vertical = 16.dp),
        columns = GridCells.Fixed(2),
        contentPadding = contentPadding
    ) {
        items(items = products, key = { it.id}) { product ->
            Product(
                product.name,
                product.amount,
                modifier = Modifier.padding(top = 0.dp, start = 8.dp, end = 8.dp, bottom = 16.dp)
            )
        }
    }
}

@Composable
fun Product(
    name: String,
    amount: Int,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .clip(ShapeDefaults.Medium)
            .background(backgroundBlue)
            .fillMaxWidth(0.5f)
            .fillMaxHeight(0.3f)
        ,
        contentAlignment = Alignment.Center
    ){
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
            ){
                Column {
                    Text(
                        text = stringResource(R.string.name, name),
                        fontSize = 24.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.amount, amount),
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
            ){
                Button(
                    onClick = {},
                    enabled = true,
                    shape = ShapeDefaults.Large,
                    colors = ButtonColors(
                        containerColor = componentBackground,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.Gray
                    )
                ){
                    Text(
                        text = "Add/Remove",
                        fontSize = 16.sp
                    )
                }
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun ProductPreview(){
    InventoryManagementTheme {
        Column {
            Product(
                "Bolts",
                50
            )
        }
    }
}
