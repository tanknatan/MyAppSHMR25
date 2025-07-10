package com.natan.shamilov.shmr25.common.network

/**
 * События, связанные с состоянием сети.
 * Ответственность: Определение типов событий, связанных с сетевым подключением,
 * для унифицированной обработки сетевых событий в UI.
 */
sealed class NetworkEvent {
    /**
     * Событие для отображения toast-сообщения об отсутствии подключения
     */
    object ShowNoConnectionToast : NetworkEvent()
}
