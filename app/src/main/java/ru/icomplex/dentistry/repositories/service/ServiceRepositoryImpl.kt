package ru.icomplex.dentistry.repositories.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.icomplex.dentistry.extension.get
import ru.icomplex.dentistry.model.service.ViewServiceList
import ru.icomplex.dentistry.sources.service.ServiceSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceRepositoryImpl @Inject constructor(
    private val serviceSource: ServiceSource
) : ServiceRepository {

    override suspend fun getServices(doctorId: Int?): Flow<ViewServiceList> = flow {
        serviceSource.getServices(doctorId).get { emit(it) }
    }
}