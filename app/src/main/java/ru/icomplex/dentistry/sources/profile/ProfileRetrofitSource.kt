package ru.icomplex.dentistry.sources.profile

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import ru.icomplex.dentistry.extension.toResult
import ru.icomplex.dentistry.model.profile.Profile
import ru.icomplex.dentistry.sources.base.BaseRetrofitSource
import ru.icomplex.dentistry.sources.base.RetrofitConfig
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Сетевой источник данных для профиля пользователя
 */
@Singleton
class ProfileRetrofitSource @Inject constructor(
    retrofitConfig: RetrofitConfig
) : BaseRetrofitSource(retrofitConfig), ProfileSource {

    private val api = retrofit.create(ProfileApi::class.java)

    /**
     * Получить профиль пользователя
     */
    override suspend fun getProfile(): Result<Profile> {
        return withContext(Dispatchers.IO) {
            api.getProfile().awaitResponse().toResult()
        }
    }
}