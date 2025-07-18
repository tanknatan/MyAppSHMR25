package com.natan.shamilov.shmr25.common.impl.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountDbModel(
    @PrimaryKey val id: Int,
    val name: String,
    val balance: Double,
    val currency: String,
)
