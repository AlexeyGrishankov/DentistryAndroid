package ru.icomplex.dentistry.repositories.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.icomplex.dentistry.extension.get
import ru.icomplex.dentistry.model.auth.AuthCode
import ru.icomplex.dentistry.model.auth.AuthGrant
import ru.icomplex.dentistry.model.auth.AuthPhone
import ru.icomplex.dentistry.model.settings.AppSettings
import ru.icomplex.dentistry.sources.auth.AuthSource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Репозиторий для авторизации по умолчанию
 *
 * @param retrofitAuthSource удаленный источник данных
 * @param appSettings настройки в приложении
 */
@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val retrofitAuthSource: AuthSource,
    private val appSettings: AppSettings,
) : AuthRepository {

    /**
     * Авторизация по номеру телефона. Отправка смс с кодом
     *
     * @param authPhone номер телефона
     */
    override suspend fun getAuthCode(authPhone: AuthPhone): Flow<AuthCode> = flow {
        retrofitAuthSource.getAuthCode(authPhone).get {
            emit(it)
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Авторизация по коду из смс
     *
     * @param authCode код из смс
     */
    override suspend fun signIn(authCode: AuthCode): Flow<AuthGrant> = flow {
        retrofitAuthSource.signIn(authCode).get {
            emit(it)
            appSettings.setCurrentToken(it.token)
        }
    }.flowOn(Dispatchers.IO)
}