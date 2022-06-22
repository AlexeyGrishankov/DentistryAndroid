package ru.icomplex.dentistry.repositories.profile

import kotlinx.coroutines.flow.Flow
import ru.icomplex.dentistry.model.profile.Profile

interface ProfileRepository {

    /**
     * Получить профиль пользователя
     */
    suspend fun getProfile(): Flow<Profile>
}