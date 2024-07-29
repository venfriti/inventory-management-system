package com.venfriti.inventorymanagement

import android.app.Application
import com.venfriti.inventorymanagement.data.AppContainer
import com.venfriti.inventorymanagement.data.AppDataContainer


class InventoryManagementApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}