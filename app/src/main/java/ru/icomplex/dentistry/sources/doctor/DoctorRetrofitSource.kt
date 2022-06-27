package ru.icomplex.dentistry.sources.doctor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import ru.icomplex.dentistry.extension.toResult
import ru.icomplex.dentistry.model.doctor.ViewDoctorList
import ru.icomplex.dentistry.sources.base.BaseRetrofitSource
import ru.icomplex.dentistry.sources.base.RetrofitConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DoctorRetrofitSource @Inject constructor(
    retrofitConfig: RetrofitConfig
) : BaseRetrofitSource(retrofitConfig), DoctorSource {

    private val api = retrofit.create(DoctorApi::class.java)

    override suspend fun getDoctors(serviceId: Int?): Result<ViewDoctorList> {
        return withContext(Dispatchers.IO) {
            api.getDoctors(serviceId).awaitResponse().toResult()
        }
    }
}