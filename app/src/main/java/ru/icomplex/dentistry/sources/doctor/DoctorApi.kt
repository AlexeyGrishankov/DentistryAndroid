package ru.icomplex.dentistry.sources.doctor

import retrofit2.Call
import retrofit2.http.GET
import ru.icomplex.dentistry.model.doctor.ViewDoctorList

interface DoctorApi {

    @GET("/api/doctors")
    fun getDoctors(): Call<ViewDoctorList>
}