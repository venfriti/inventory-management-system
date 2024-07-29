package com.venfriti.inventorymanagement.data

import android.content.Context

interface AppContainer{
    val inventoryRepository: InventoryRepository
}

class AppDataContainer(private val context: Context): AppContainer {

    override val inventoryRepository: InventoryRepository by lazy {
        OfflineInventoryRepository(AppDatabase.getDatabase(context).inventoryDao())
    }
}