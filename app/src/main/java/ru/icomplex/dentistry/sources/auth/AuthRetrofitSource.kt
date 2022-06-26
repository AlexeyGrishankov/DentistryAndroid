package ru.icomplex.dentistry.sources.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import ru.icomplex.dentistry.extension.toResult
import ru.icomplex.dentistry.model.auth.AuthCode
import ru.icomplex.dentistry.model.auth.AuthGrant
import ru.icomplex.dentistry.model.auth.AuthPhone
import ru.icomplex.dentistry.sources.base.BaseRetrofitSource
import ru.icomplex.dentistry.sources.base.RetrofitConfig
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Сетевой источник данных для авторизации
 */
@Singleton
class AuthRetrofitSource @Inject constructor(
    retrofitConfig: RetrofitConfig
) : BaseRetrofitSource(retrofitConfig), AuthSource {

    private val api = retrofit.create(AuthApi::class.java)

    /**
     * Авторизация по номеру телефона. Отправка смс с кодом
     *
     * @param authPhone номер телефона
     */
    override suspend fun getAuthCode(authPhone: AuthPhone): Result<AuthCode> {
        return withContext(Dispatchers.IO) {
            api.getAuthCode(authPhone).awaitResponse().toResult()
        }
    }

    /**
     * Авторизация по коду из смс
     *
     * @param authCode код из смс
     */
    override suspend fun signIn(authCode: AuthCode): Result<AuthGrant> {
        return withContext(Dispatchers.IO) {
            api.signIn(authCode).awaitResponse().toResult()
        }
    }
}