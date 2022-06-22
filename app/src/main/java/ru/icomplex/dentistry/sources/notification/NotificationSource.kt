package ru.icomplex.dentistry.sources.notification

import ru.icomplex.dentistry.model.notification.NotificationList

interface NotificationSource {

    suspend fun getNotifications(): Result<NotificationList>
}