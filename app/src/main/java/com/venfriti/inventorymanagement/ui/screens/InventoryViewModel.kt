package com.venfriti.inventorymanagement.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venfriti.inventorymanagement.data.Inventory
import com.venfriti.inventorymanagement.data.InventoryRepository
import kotlinx.coroutines.launch


class InventoryViewModel(
    savedStateHandle: SavedStateHandle,
    inventoryRepository: InventoryRepository
): ViewModel() {
    init {
        viewModelScope.launch {
            inventoryRepository.addInventory(Inventory(name = "Indomie", amount = 50))
        }
    }

}