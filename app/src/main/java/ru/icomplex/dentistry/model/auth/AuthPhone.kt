package ru.icomplex.dentistry.model.auth

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AuthPhone(
    @SerializedName("phone") val phone: String
) : Serializable
