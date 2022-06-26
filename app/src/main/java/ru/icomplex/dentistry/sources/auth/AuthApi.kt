package ru.icomplex.dentistry.sources.auth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.icomplex.dentistry.di.modules.NetworkModule
import ru.icomplex.dentistry.model.auth.AuthCode
import ru.icomplex.dentistry.model.auth.AuthGrant
import ru.icomplex.dentistry.model.auth.AuthPhone

/**
 * REST API для работы с данными авторизации
 */
interface AuthApi {

    /**
     * Авторизация по номеру телефона. Отправка смс с кодом
     *
     * @param authPhone номер телефона
     */
    @POST("/api/auth/phone")
    @Headers("${NetworkModule.CACHE_CONTROL_HEADER}: ${NetworkModule.CACHE_CONTROL_NO_CACHE}")
    fun getAuthCode(@Body authPhone: AuthPhone): Call<AuthCode>

    /**
     * Авторизация по коду из смс
     *
     * @param authCode код из смс
     */
    @POST("/api/auth/code")
    @Headers("${NetworkModule.CACHE_CONTROL_HEADER}: ${NetworkModule.CACHE_CONTROL_NO_CACHE}")
    fun signIn(@Body authCode: AuthCode): Call<AuthGrant>

//    /**
//     * Обновить токен доступа
//     */
//    @POST("/api/auth/refresh")
//    @Headers("${NetworkModule.CACHE_CONTROL_HEADER}: ${NetworkModule.CACHE_CONTROL_NO_CACHE}")
//    fun refreshToken(): Call<AuthGrant>
}