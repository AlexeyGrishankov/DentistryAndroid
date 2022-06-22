package ru.icomplex.dentistry.sources.notification

import retrofit2.Call
import retrofit2.http.GET
import ru.icomplex.dentistry.model.notification.NotificationList

interface NotificationApi {

    @GET("/api/notification")
    suspend fun getNotifications(): Call<NotificationList>
}