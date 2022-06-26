package ru.icomplex.dentistry.sources.notification

import retrofit2.Call
import retrofit2.http.GET
import ru.icomplex.dentistry.model.notification.ViewNotificationList

interface NotificationApi {

    @GET("/api/notification")
    fun getNotifications(): Call<ViewNotificationList>
}