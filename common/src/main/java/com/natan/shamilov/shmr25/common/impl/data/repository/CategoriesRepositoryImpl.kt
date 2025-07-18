package com.natan.shamilov.shmr25.common.impl.data.repository

import android.util.Log
import com.natan.shamilov.shmr25.common.api.CategoriesProvider
import com.natan.shamilov.shmr25.common.api.NetworkChekerProvider
import com.natan.shamilov.shmr25.common.impl.data.api.CategoriesApi
import com.natan.shamilov.shmr25.common.impl.data.mapper.CategoriesMapper
import com.natan.shamilov.shmr25.common.impl.domain.entity.Category
import com.natan.shamilov.shmr25.common.impl.domain.repository.CategoriesRepository
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import com.natan.shamilov.shmr25.common.impl.data.storage.dao.CategoriesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Реализация репозитория для работы с категориями.
 * Ответственность: Реализация операций с категориями, включая получение данных
 * из API и их преобразование в доменные модели.
 *
 * @property api API для работы с категориями
 * @property mapper Маппер для преобразования DTO в доменные модели
 */
class CategoriesRepositoryImpl @Inject constructor(
    private val api: CategoriesApi,
    private val mapper: CategoriesMapper,
    private val networkRepository: NetworkChekerProvider,
    private val categoriesDao: CategoriesDao,
) : CategoriesRepository, CategoriesProvider {

    var categoriesList = listOf<Category>()

    override suspend fun getCategoriesList(): List<Category> {
        if (!categoriesList.isEmpty()) return categoriesList
        else {
            when (val result = loadCategoriesList()) {
                is Result.Success<List<Category>> -> {
                    categoriesList = result.data
                }
                else -> {
                    Log.d("LoadCategoriesTest", result.toString())
                }
            }
            return categoriesList
        }
    }

    private suspend fun loadCategoriesList(): Result<List<Category>> = Result.execute {
        if (networkRepository.getNetworkStatus().value) {
            loadCategoriesFromNetwork()
        }
        else {
            loadCategoriesFromDb()
        }
    }

    private suspend fun loadCategoriesFromNetwork(): List<Category> {
        val categoriesDto = api.getCategories()
        val categoriesDomain = categoriesDto.map { categoryDto ->
            mapper.mapCategoryDtoToDomain(categoryDto)
        }

        categoriesDao.insertCategories(categoriesDomain.map { mapper.mapDomainToDb(it) })

        return categoriesDomain
    }

    private suspend fun loadCategoriesFromDb(): List<Category> {
        val dbCategories = categoriesDao.getAllCategories()

        return dbCategories.map { dbCategory ->
            mapper.mapDbToDomain(dbCategory)
        }
    }
}
