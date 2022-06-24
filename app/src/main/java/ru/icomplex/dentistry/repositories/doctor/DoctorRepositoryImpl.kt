package ru.icomplex.dentistry.repositories.doctor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.icomplex.dentistry.extension.get
import ru.icomplex.dentistry.model.doctor.ViewDoctorList
import ru.icomplex.dentistry.sources.doctor.DoctorSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DoctorRepositoryImpl @Inject constructor(
    private val doctorSource: DoctorSource
) : DoctorRepository{
    override suspend fun getDoctors(): Flow<ViewDoctorList> = flow {
        doctorSource.getDoctors().get { emit(it) }
    }
}