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
        const val USER_TOKEN = "current_user_token"
        const val SECRET_SF = "secret_shared_prefs"
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
}