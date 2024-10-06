package com.venfriti.inventorymanagement.data

import kotlinx.coroutines.flow.Flow


class OfflineInventoryRepository(private val inventoryDao: InventoryDao): InventoryRepository {
    override fun getAllInventory(
    ): Flow<List<Inventory>> = inventoryDao.getAllInventory()

    override suspend fun addInventory(
        inventory: Inventory
    ) = inventoryDao.addInventory(inventory)

    override suspend fun addInventoryList(
        inventoryList: List<Inventory>
    ) = inventoryDao.addInventoryList(inventoryList)

    override suspend fun updateInventory(inventory: Inventory) = inventoryDao.updateInventory(inventory)
    override suspend fun deleteInventory(id: Int)  = inventoryDao.deleteInventory(id)

    override fun getSearchList(
        searchName: String
    ): Flow<List<Inventory>> = inventoryDao.getSearchList(searchName)
}