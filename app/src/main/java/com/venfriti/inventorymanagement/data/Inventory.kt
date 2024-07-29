package com.venfriti.inventorymanagement.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "inventory")
data class Inventory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val amount: Int
)