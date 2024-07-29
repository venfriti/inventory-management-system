package com.venfriti.inventorymanagement.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.venfriti.inventorymanagement.InventoryApplication
import com.venfriti.inventorymanagement.ui.screens.InventoryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            InventoryViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.inventoryRepository
            )
        }
    }
}

fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)