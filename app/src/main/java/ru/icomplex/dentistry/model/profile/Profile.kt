package ru.icomplex.dentistry.model.profile

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Profile(
    @SerializedName("email") val email: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("history") val history: List<String>,
) : Serializable
