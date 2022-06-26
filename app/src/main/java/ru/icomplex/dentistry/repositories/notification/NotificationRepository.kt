package ru.icomplex.dentistry.repositories.notification

import kotlinx.coroutines.flow.Flow
import ru.icomplex.dentistry.model.notification.ViewNotificationList

interface NotificationRepository {

    suspend fun getNotifications(): Flow<ViewNotificationList>
}