package com.natan.shamilov.shmr25.common.impl.data.mapper

import com.natan.shamilov.shmr25.common.impl.data.model.CategoryDto
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
}
