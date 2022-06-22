package ru.icomplex.dentistry.model.auth

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ViewUser(
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String,
    @SerializedName("full_name") val fullName: String,
) : Serializable