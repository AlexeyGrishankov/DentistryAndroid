package ru.icomplex.dentistry.sources.doctor

import ru.icomplex.dentistry.model.doctor.ViewDoctorList

interface DoctorSource {

    suspend fun getDoctors(): Result<ViewDoctorList>
}