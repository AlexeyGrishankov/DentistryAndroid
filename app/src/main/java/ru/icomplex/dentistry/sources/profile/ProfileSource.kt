package ru.icomplex.dentistry.sources.profile

import ru.icomplex.dentistry.model.profile.Profile

interface ProfileSource {

    /**
     * Получить профиль пользователя
     */
    suspend fun getProfile(): Result<Profile>
}