package ru.icomplex.dentistry.model.notification

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ViewNotificationList(
    @SerializedName("data") val data: List<ViewNotification>
) : Serializable