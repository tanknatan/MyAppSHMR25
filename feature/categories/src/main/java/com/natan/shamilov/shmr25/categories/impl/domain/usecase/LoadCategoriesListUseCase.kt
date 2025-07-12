package com.natan.shamilov.shmr25.categories.impl.domain.usecase

import com.natan.shamilov.shmr25.categories.impl.domain.repository.CategoriesRepository
import com.natan.shamilov.shmr25.common.impl.data.model.Result
import javax.inject.Inject

/**
 * Use case для получения списка категорий.
 * Ответственность: Реализация бизнес-логики получения списка всех доступных категорий.
 * Делегирует фактическое получение данных репозиторию.
 *
 * @property repository Репозиторий для работы с категориями
 */
class LoadCategoriesListUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository,
) {
    /**
     * Выполняет получение списка категорий
     * @return результат операции со списком категорий
     */
    suspend operator fun invoke(): Result<Unit> = categoriesRepository.loadCategoriesList()
}
