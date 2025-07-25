package com.natan.shamilov.shmr25.common.impl.domain.entity

enum class HapticType(val value: String) {
    SHORT("short"),
    MEDIUM("medium"),
    LONG("long");

    companion object {
        fun fromValue(value: String): HapticType {
            return values().find { it.value == value } ?: SHORT
        }

        fun getDefault(): HapticType = SHORT
    }
}