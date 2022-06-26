package ru.icomplex.dentistry.sources.auth

import ru.icomplex.dentistry.model.auth.AuthCode
import ru.icomplex.dentistry.model.auth.AuthGrant
import ru.icomplex.dentistry.model.auth.AuthPhone

/**
 * Источник данных для авторизации
 */
interface AuthSource {

    /**
     * Авторизация по номеру телефона. Отправка смс с кодом
     *
     * @param authPhone номер телефона
     */
    suspend fun getAuthCode(authPhone: AuthPhone): Result<AuthCode>

    /**
     * Авторизация по коду из смс
     *
     * @param authCode код из смс
     */
    suspend fun signIn(authCode: AuthCode): Result<AuthGrant>
}