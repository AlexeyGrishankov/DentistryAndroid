package ru.icomplex.dentistry.model.notification

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NotificationList(
    @SerializedName("data") val data: List<Notification>
) : Serializable