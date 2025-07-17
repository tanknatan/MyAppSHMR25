package com.natan.shamilov.shmr25.common.impl.domain.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.natan.shamilov.shmr25.common.impl.data.storage.entity.AccountDbModel
import com.natan.shamilov.shmr25.common.impl.data.storage.entity.CategoryDbModel

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = AccountDbModel::class,
            parentColumns = ["id"],
            childColumns = ["account_id"]
        ),
        ForeignKey(
            entity = CategoryDbModel::class,
            parentColumns = ["id"],
            childColumns = ["category_id"]
        )
    ]
)
data class TransactionDbModel(
    @PrimaryKey val id: Int,
    val amount: Double,
    val comment: String?,
    @ColumnInfo("account_id") val accountId: Int,
    @ColumnInfo("category_id") val categoryId: Int,
    @ColumnInfo("created_at") val createdAt: String,
    @ColumnInfo("sync_status") val syncStatus: String = "synced", // synced, pending, failed
    @ColumnInfo("local_id") val localId: String? = null, // для локальных ID
    @ColumnInfo("created_at_local") val createdAtLocal: Long = System.currentTimeMillis(),
    @ColumnInfo("updated_at") val updatedAt: Long = System.currentTimeMillis(),
)
