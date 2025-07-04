package com.natan.shamilov.shmr25.feature.categories.data.mapper

import com.natan.shamilov.shmr25.feature.categories.data.model.CategoryDto
import com.natan.shamilov.shmr25.feature.categories.domain.entity.Category
import javax.inject.Inject

class CategoryMapper @Inject constructor() {
    fun mapDtoToDomain(dto: CategoryDto): Category = with(dto) {
        Category(
            id = id,
            name = name,
            emoji = emoji,
            isIncome = isIncome
        )
    }
} 