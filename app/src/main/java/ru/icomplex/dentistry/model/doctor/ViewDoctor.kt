package ru.icomplex.dentistry.model.doctor

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ViewDoctor(
    @SerializedName("id") val id: Int,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("img_url") val imgUrl: String,
    @SerializedName("position") val positions: String,
    @SerializedName("price") val price: Int,
): Serializable
