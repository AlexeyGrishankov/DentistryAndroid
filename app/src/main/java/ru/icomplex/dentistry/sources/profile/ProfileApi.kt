package ru.icomplex.dentistry.sources.profile

import retrofit2.Call
import retrofit2.http.GET
import ru.icomplex.dentistry.model.profile.Profile

/**
 * REST API для работы с данными профиля пользователя
 */
interface ProfileApi {

    /**
     * Получить профиль пользователя
     */
    @GET("/api/profile")
    suspend fun getProfile(): Call<Profile>
}