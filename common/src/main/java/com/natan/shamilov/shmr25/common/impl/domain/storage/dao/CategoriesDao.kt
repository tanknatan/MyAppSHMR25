package com.natan.shamilov.shmr25.common.impl.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.natan.shamilov.shmr25.common.impl.data.storage.entity.CategoryDbModel

@Dao
interface CategoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryDbModel: CategoryDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categoryDbModel: List<CategoryDbModel>)

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryDbModel>

    @Query("DELETE FROM categories")
    suspend fun clearAll()
}