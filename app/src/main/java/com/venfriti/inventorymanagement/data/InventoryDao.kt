package com.venfriti.inventorymanagement.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao{

    @Query("SELECT * FROM inventory ORDER BY id ASC")
    fun getAllInventory(): Flow<List<Inventory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addInventory(inventory: Inventory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addInventoryList(inventoryList: List<Inventory>)

    @Query("SELECT * FROM inventory WHERE name LIKE '%' || :searchName || '%' ORDER BY id ASC")
    fun getSearchList(searchName: String): Flow<List<Inventory>>

    @Update
    suspend fun updateInventory(inventory: Inventory)

}