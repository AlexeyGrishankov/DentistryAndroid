package ru.icomplex.dentistry.repositories.doctor

import kotlinx.coroutines.flow.Flow
import ru.icomplex.dentistry.model.doctor.ViewDoctorList

interface DoctorRepository {

    suspend fun getDoctors(): Flow<ViewDoctorList>
}