package ru.icomplex.dentistry.repositories.notification

import kotlinx.coroutines.flow.Flow
import ru.icomplex.dentistry.model.notification.NotificationList

interface NotificationRepository {

    suspend fun getNotifications(): Flow<NotificationList>
}