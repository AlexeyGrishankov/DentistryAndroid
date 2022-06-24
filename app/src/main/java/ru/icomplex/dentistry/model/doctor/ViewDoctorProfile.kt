package ru.icomplex.dentistry.model.doctor

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ViewDoctorProfile(
    @SerializedName("id") val id: Int,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("position") val position: String,
    @SerializedName("img_url") val imgUrl: String,
    @SerializedName("price") val price: Int,
    @SerializedName("note") val note: String,
) : Serializable