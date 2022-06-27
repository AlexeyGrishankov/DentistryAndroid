package ru.icomplex.dentistry.sources.doctor

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.icomplex.dentistry.model.doctor.ViewDoctorList

interface DoctorApi {

    @GET("/api/doctors")
    fun getDoctors(@Query("service_id") serviceId: Int?): Call<ViewDoctorList>
}