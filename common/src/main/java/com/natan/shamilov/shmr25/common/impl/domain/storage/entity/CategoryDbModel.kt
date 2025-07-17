package com.natan.shamilov.shmr25.common.impl.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryDbModel(
    @PrimaryKey val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
)