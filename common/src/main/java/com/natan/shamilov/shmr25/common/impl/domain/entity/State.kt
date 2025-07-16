package com.natan.shamilov.shmr25.common.impl.domain.entity

/**
 * Представляет состояния UI в приложении.
 * Ответственность: Определение всех возможных состояний экрана (загрузка, ошибка, контент)
 * для унифицированной обработки состояний UI во всем приложении.
 */
sealed interface State {
    /**
     * Состояние загрузки данных
     */
    data object Loading : State

    /**
     * Состояние ошибки
     */
    data object Error : State

    /**
     * Состояние успешной загрузки контента
     */
    data object Content : State
}
