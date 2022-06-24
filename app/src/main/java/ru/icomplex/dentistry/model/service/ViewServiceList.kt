package ru.icomplex.dentistry.model.service

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ViewServiceList(
    @SerializedName("data") val data: List<ViewService>
) : Serializable
