package com.venfriti.inventorymanagement.data

import kotlinx.coroutines.flow.Flow

interface InventoryRepository {

    fun getAllInventory(): Flow<List<Inventory>>

    suspend fun addInventory(inventory: Inventory)

    suspend fun addInventoryList(inventoryList: List<Inventory>)
}