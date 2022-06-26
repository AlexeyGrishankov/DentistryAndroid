package ru.icomplex.dentistry.model.doctor

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ViewDoctorList(
    @SerializedName("data") val data: List<ViewDoctor>
): Serializable