package ru.icomplex.dentistry.sources.service

import retrofit2.Call
import retrofit2.http.GET
import ru.icomplex.dentistry.model.service.ViewServiceList

interface ServiceApi {

    @GET("/api/services")
    fun getServices(): Call<ViewServiceList>

}