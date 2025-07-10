package com.natan.shamilov.shmr25.feature.categories.data.repository

import com.natan.shamilov.shmr25.common.data.model.Result
import com.natan.shamilov.shmr25.feature.categories.data.api.CategoriesApi
import com.natan.shamilov.shmr25.common.data.mapper.CategoriesMapper
import com.natan.shamilov.shmr25.common.domain.entity.Category
import com.natan.shamilov.shmr25.feature.categories.domain.repository.CategoriesRepository
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
) : CategoriesRepository {

    private var categoriesList = emptyList<Category>()

    override suspend fun getCategoriesList(): List<Category> = withContext(Dispatchers.IO) {
        categoriesList
    }

    /**
     * Получает список категорий из API и преобразует их в доменные модели
     * @return результат операции со списком категорий
     */
    override suspend fun loadCategoriesList() = Result.execute {
        categoriesList = api.getCategories().map { dto ->
            mapper.mapCategoryDtoToDomain(dto)
        }
    }
}
