package ru.icomplex.dentistry.repositories.service

import kotlinx.coroutines.flow.Flow
import ru.icomplex.dentistry.model.service.ViewServiceList

interface ServiceRepository {

    suspend fun getServices(): Flow<ViewServiceList>
}