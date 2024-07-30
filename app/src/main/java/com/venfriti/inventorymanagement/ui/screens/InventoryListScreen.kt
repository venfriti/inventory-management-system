package com.venfriti.inventorymanagement.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.venfriti.inventorymanagement.R
import com.venfriti.inventorymanagement.ui.theme.backgroundBlue


@Composable
fun InventoryHomeScreen(viewModel: InventoryViewModel, contentPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(contentPadding)
            .padding(horizontal = 48.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            var holder by rememberSaveable { mutableStateOf("") }
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
                    searchQuery = holder,
                    onSearch = { holder = it },
                    onValueChange = {
//                        show = true
                        holder = it
//                        viewModel.setSearchQuery(it)
                    },
                    onClear = {
//                        show = true
                        holder = ""
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
        Product(
            "Bolts",
            50
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
            .height(55.dp),
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
fun Product(
    name: String,
    amount: Int
){
    Box(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .clip(ShapeDefaults.Medium)
            .background(backgroundBlue)
            .fillMaxWidth(0.5f)
            .fillMaxHeight(0.3f),
        contentAlignment = Alignment.Center
    ){
        Row(
        ){
            Box(
                modifier = Modifier.fillMaxHeight().weight(3f),
                contentAlignment = Alignment.Center
            ){
                Column(
                ) {
                    Text(
                        text = stringResource(R.string.name, name),
                        fontSize = 32.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.amount, amount),
                        fontSize = 32.sp
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(3f),
                contentAlignment = Alignment.Center
            ){
                Button(
                    onClick = {},
                    enabled = true,
                    shape = ShapeDefaults.Large
                ){
                    Text(
                        text = "Add/Remove",
                        fontSize = 20.sp
                    )
                }
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun ProductPreview(){
    Product(
        "Bolts",
        50
    )
}
