package com.venfriti.inventorymanagement

import android.app.Application
import com.venfriti.inventorymanagement.data.AppContainer
import com.venfriti.inventorymanagement.data.AppDataContainer


class InventoryApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}