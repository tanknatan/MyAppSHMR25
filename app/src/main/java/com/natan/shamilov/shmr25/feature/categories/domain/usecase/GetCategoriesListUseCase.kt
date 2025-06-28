package com.natan.shamilov.shmr25.feature.categories.domain.usecase

import com.natan.shamilov.shmr25.app.data.api.Result
import com.natan.shamilov.shmr25.common.Category
import com.natan.shamilov.shmr25.feature.categories.domain.repository.CategoriesRepository
import javax.inject.Inject

/**
 * Use case для получения списка категорий.
 * Ответственность: Реализация бизнес-логики получения списка всех доступных категорий.
 * Делегирует фактическое получение данных репозиторию.
 *
 * @property repository Репозиторий для работы с категориями
 */
class GetCategoriesListUseCase @Inject constructor(
    private val repository: CategoriesRepository
) {
    /**
     * Выполняет получение списка категорий
     * @return результат операции со списком категорий
     */
    suspend operator fun invoke(): Result<List<Category>> = repository.getCategoriesList()
}
