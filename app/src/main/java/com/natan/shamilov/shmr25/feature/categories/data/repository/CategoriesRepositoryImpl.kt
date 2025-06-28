package com.natan.shamilov.shmr25.feature.categories.data.repository

import com.natan.shamilov.shmr25.app.data.api.Result
import com.natan.shamilov.shmr25.app.data.api.mapper.FinanceMapper
import com.natan.shamilov.shmr25.common.Category
import com.natan.shamilov.shmr25.feature.categories.data.api.CategoriesApi
import com.natan.shamilov.shmr25.feature.categories.domain.repository.CategoriesRepository
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
    private val mapper: FinanceMapper
) : CategoriesRepository {

    /**
     * Получает список категорий из API и преобразует их в доменные модели
     * @return результат операции со списком категорий
     */
    override suspend fun getCategoriesList(): Result<List<Category>> = Result.execute {
        api.getCategories().map { dto ->
            mapper.mapCategoryDtoToDomain(dto)
        }
    }
}
