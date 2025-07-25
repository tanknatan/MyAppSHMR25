package com.natan.shamilov.shmr25.common.impl.presentation.ui.theme

enum class MainColorType(val color: String) {

    GREEN("green"),
    RED("red"),
    YELLOW("yellow"),
    BLUE("blue");

    companion object {
        fun fromValue(color: String): MainColorType {
            return MainColorType.entries.find { it.color == color } ?: RED
        }

        fun getDefault(): MainColorType = BLUE
    }
}
