package ru.icomplex.dentistry.sources.notification

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import ru.icomplex.dentistry.extension.toResult
import ru.icomplex.dentistry.model.notification.ViewNotificationList
import ru.icomplex.dentistry.sources.base.BaseRetrofitSource
import ru.icomplex.dentistry.sources.base.RetrofitConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRetrofitSource @Inject constructor(
    retrofitConfig: RetrofitConfig
) : BaseRetrofitSource(retrofitConfig), NotificationSource {

    private val api = retrofit.create(NotificationApi::class.java)

    override suspend fun getNotifications(): Result<ViewNotificationList> {
        return withContext(Dispatchers.IO) {
            api.getNotifications().awaitResponse().toResult()
        }
    }
}