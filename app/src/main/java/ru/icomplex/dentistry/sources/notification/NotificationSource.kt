package ru.icomplex.dentistry.sources.notification

import ru.icomplex.dentistry.model.notification.ViewNotificationList

interface NotificationSource {

    suspend fun getNotifications(): Result<ViewNotificationList>
}