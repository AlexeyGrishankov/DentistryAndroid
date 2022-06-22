package ru.icomplex.dentistry.model.notification

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Notification(
    @SerializedName("id") val id: Int,
    @SerializedName("header") val header: String,
    @SerializedName("body") val body: String,
) : Serializable