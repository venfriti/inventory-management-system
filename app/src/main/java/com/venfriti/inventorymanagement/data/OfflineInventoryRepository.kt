package com.venfriti.inventorymanagement.data

import kotlinx.coroutines.flow.Flow


class OfflineInventoryRepository(private val inventoryDao: InventoryDao): InventoryRepository {
    override fun getAllInventory(): Flow<List<Inventory>> = inventoryDao.getAllInventory()
}