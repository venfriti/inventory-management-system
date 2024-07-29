package com.venfriti.inventorymanagement.data

import kotlinx.coroutines.flow.Flow

interface InventoryRepository {
    fun getAllInventory(): Flow<List<Inventory>>
}