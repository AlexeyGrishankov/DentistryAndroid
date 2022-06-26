package ru.icomplex.dentistry.model.auth

import com.google.gson.annotations.SerializedName

data class AuthGrant(
    @SerializedName("expires_in") val expiresIn: Int,
    @SerializedName("token") val token: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("user") val user: ViewUser
)