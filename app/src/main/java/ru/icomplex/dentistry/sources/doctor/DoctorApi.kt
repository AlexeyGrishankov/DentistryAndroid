package ru.icomplex.dentistry.sources.doctor

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.icomplex.dentistry.model.doctor.ViewDoctorList
import ru.icomplex.dentistry.model.doctor.ViewDoctorProfile

interface DoctorApi {

    @GET("/api/doctors")
    fun getDoctors(): Call<ViewDoctorList>
}