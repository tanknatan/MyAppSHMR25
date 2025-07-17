package com.natan.shamilov.shmr25.common.impl.data.mapper

import com.natan.shamilov.shmr25.common.impl.data.model.CategoryDto
import com.natan.shamilov.shmr25.common.impl.data.storage.entity.CategoryDbModel
import com.natan.shamilov.shmr25.common.impl.domain.entity.Category
import javax.inject.Inject

/**
 * Маппер для преобразования DTO категорий в доменные модели.
 * Обеспечивает конвертацию данных между слоями data и domain.
 */
class CategoriesMapper @Inject constructor() {

    fun mapCategoryDtoToDomain(dto: CategoryDto): Category = with(dto) {
        Category(
            id = id,
            name = name,
            emoji = emoji,
            isIncome = isIncome
        )
    }

    fun mapDbToDomain(categoryDb: CategoryDbModel): Category {
        return Category(
            id = categoryDb.id.toLong(),
            name = categoryDb.name,
            isIncome = categoryDb.isIncome,
            emoji = categoryDb.emoji
        )
    }

    fun mapDomainToDb(category: Category): CategoryDbModel {
        return CategoryDbModel(
            id = category.id.toInt(),
            name = category.name,
            isIncome = category.isIncome,
            emoji = category.emoji
        )
    }
}
