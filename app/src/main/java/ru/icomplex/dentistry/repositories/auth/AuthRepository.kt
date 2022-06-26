package ru.icomplex.dentistry.repositories.auth

import kotlinx.coroutines.flow.Flow
import ru.icomplex.dentistry.model.auth.AuthCode
import ru.icomplex.dentistry.model.auth.AuthGrant
import ru.icomplex.dentistry.model.auth.AuthPhone

/**
 * Репозиторий для авторизации
 */
interface AuthRepository {

    /**
     * Авторизация по номеру телефона. Отправка смс с кодом
     *
     * @param authPhone номер телефона
     */
    suspend fun getAuthCode(authPhone: AuthPhone): Flow<AuthCode>

    /**
     * Авторизация по коду из смс
     *
     * @param authCode код из смс
     */
    suspend fun signIn(authCode: AuthCode): Flow<AuthGrant>
}