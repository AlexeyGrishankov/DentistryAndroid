package ru.icomplex.dentistry.model.settings


import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Локальное хранилище настроек приложения
 */
@Singleton
class SharedPreferencesAppSettings @Inject constructor(
    @ApplicationContext appContext: Context
) : AppSettings {

    companion object {
        const val SECRET_SF = "secret_shared_prefs"
        const val USER_TOKEN = "current_user_token"
        const val NOTIFICATIONS_IDS = "notification_ids"
    }

    private val sharedPreferences = appContext.getSharedPreferences(SECRET_SF, Context.MODE_PRIVATE)

    /**
     * Получить текущий токен
     */
    override fun getCurrentToken(): String? {
        return sharedPreferences.getString(USER_TOKEN, null)
    }

    /**
     * Установить текущий токен
     */
    override fun setCurrentToken(token: String?) {
        val editor = sharedPreferences.edit()
        if (token == null) {
            editor.remove(USER_TOKEN)
        } else {
            editor.putString(USER_TOKEN, token)
        }
        editor.apply()
    }

    override fun getNotificationsIds(): IntArray {
        return sharedPreferences.getStringSet(NOTIFICATIONS_IDS, emptySet())
            ?.map { it.toInt() }
            ?.toIntArray()
            ?: IntArray(0)
    }

    override fun addNotificationsId(id: Int) {
        val currentIds = getNotificationsIds().toMutableSet().apply { add(id) }
        sharedPreferences.edit()
            .putStringSet(NOTIFICATIONS_IDS, currentIds.map { "$it" }.toSet())
            .apply()
    }
}