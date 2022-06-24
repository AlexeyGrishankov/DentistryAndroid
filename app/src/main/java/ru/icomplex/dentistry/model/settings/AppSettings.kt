package ru.icomplex.dentistry.model.settings

/**
 * Настройки приложения
 */
interface AppSettings {

    /**
     * Получить текущий токен
     */
    fun getCurrentToken(): String?

    /**
     * Установить текущий токен
     */
    fun setCurrentToken(token: String?)

    fun getNotificationsIds(): IntArray

    fun addNotificationsId(id: Int)
}