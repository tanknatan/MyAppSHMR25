package com.natan.shamilov.shmr25.common.impl.domain.storage.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.natan.shamilov.shmr25.common.impl.data.storage.entity.AccountDbModel
import com.natan.shamilov.shmr25.common.impl.data.storage.entity.CategoryDbModel

data class TransactionWithRelations(
    @Embedded val transaction: TransactionDbModel,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: CategoryDbModel,
    @Relation(
        parentColumn = "account_id",
        entityColumn = "id"
    )
    val account: AccountDbModel
)
