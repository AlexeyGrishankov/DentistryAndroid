package ru.icomplex.dentistry.model.service

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ViewService(
    @SerializedName("id") val id: Int,
    @SerializedName("price") val price: Int,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("duration") val duration: String
) : Serializable