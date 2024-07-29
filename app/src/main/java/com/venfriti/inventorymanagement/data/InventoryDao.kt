package com.venfriti.inventorymanagement.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao{
    @Query("SELECT * FROM inventory ORDER BY id ASC")
    fun getAllInventory(): Flow<List<Inventory>>

    @Insert
    suspend fun addInventory(inventory: Inventory)
}