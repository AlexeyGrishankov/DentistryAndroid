package ru.icomplex.dentistry.sources.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import ru.icomplex.dentistry.extension.toResult
import ru.icomplex.dentistry.model.service.ViewServiceList
import ru.icomplex.dentistry.sources.base.BaseRetrofitSource
import ru.icomplex.dentistry.sources.base.RetrofitConfig
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Сетевой источник данных для профиля пользователя
 */
@Singleton
class ServiceRetrofitSource @Inject constructor(
    retrofitConfig: RetrofitConfig
) : BaseRetrofitSource(retrofitConfig), ServiceSource {

    private val api = retrofit.create(ServiceApi::class.java)

    override suspend fun getServices(): Result<ViewServiceList> {
        return withContext(Dispatchers.IO) {
            api.getServices().awaitResponse().toResult()
        }
    }
}