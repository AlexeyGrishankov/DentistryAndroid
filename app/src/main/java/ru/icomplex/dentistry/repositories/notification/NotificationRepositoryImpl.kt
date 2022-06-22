package ru.icomplex.dentistry.repositories.notification

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.icomplex.dentistry.extension.get
import ru.icomplex.dentistry.model.notification.NotificationList
import ru.icomplex.dentistry.sources.notification.NotificationSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepositoryImpl @Inject constructor(
    private val notificationRetrofitSource: NotificationSource
) : NotificationRepository {

    override suspend fun getNotifications(): Flow<NotificationList> = flow {
        notificationRetrofitSource.getNotifications().get {
            emit(it)
        }
    }
}