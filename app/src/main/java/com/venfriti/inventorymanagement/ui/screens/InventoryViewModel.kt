package com.venfriti.inventorymanagement.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venfriti.inventorymanagement.data.Inventory
import com.venfriti.inventorymanagement.data.InventoryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch


class InventoryViewModel(
    savedStateHandle: SavedStateHandle,
    private val inventoryRepository: InventoryRepository
): ViewModel() {

    private val products = listOf(
        Inventory(1, "bolts", 5),
        Inventory(2, "nails", 10),
        Inventory(3, "tables", 5),
        Inventory(4, "chairs", 10),
        Inventory(5, "panes", 5),
        Inventory(6, "bowls", 10),
        Inventory(7, "fans", 5),
        Inventory(8, "boxes", 10),
    )

    init {

    }

    fun addInventoryList() {
        viewModelScope.launch {
            inventoryRepository.addInventoryList(products)
        }
    }

    val inventoryList = inventoryRepository.getAllInventory()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults = _searchQuery.flatMapLatest { query ->
        inventoryRepository.getSearchList(query)
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addInventory(product: String, amount: Int) {
        val inventory = Inventory(
            name = product,
            amount = amount
        )
        viewModelScope.launch {
            inventoryRepository.addInventory(inventory)
        }

    }

    fun deleteInventory(product: Inventory) {
        viewModelScope.launch {
            inventoryRepository.deleteInventory(product.id)
        }
    }

    fun addStock(product: Inventory, stockSize: Int) {
        val newSize = product.amount + stockSize
        product.amount = newSize
        viewModelScope.launch {
            inventoryRepository.updateInventory(product)
        }
    }

    fun removeStock(product: Inventory, stockSize: Int) {
        val newSize = product.amount - stockSize
        product.amount = newSize
        viewModelScope.launch {
            inventoryRepository.updateInventory(product)
        }
    }

}