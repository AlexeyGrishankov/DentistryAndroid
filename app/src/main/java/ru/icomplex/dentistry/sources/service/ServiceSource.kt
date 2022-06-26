package ru.icomplex.dentistry.sources.service

import ru.icomplex.dentistry.model.service.ViewServiceList


interface ServiceSource {

    suspend fun getServices(): Result<ViewServiceList>
}