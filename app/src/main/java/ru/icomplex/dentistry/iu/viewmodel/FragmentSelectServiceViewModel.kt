package ru.icomplex.dentistry.iu.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import ru.icomplex.dentistry.extension.launch
import ru.icomplex.dentistry.extension.toLiveData
import ru.icomplex.dentistry.model.service.ViewService
import ru.icomplex.dentistry.model.service.ViewServiceList
import ru.icomplex.dentistry.repositories.service.ServiceRepository
import javax.inject.Inject

@HiltViewModel
class FragmentSelectServiceViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository,
) : ViewModel() {

    private val _services = MutableLiveData<ViewServiceList>()
    val services = _services.toLiveData()

    private val _servicesTypes = MutableLiveData<List<String>>()
    val servicesTypes = _servicesTypes.toLiveData()

    private var serviceListCashed: List<ViewService> = emptyList()

    fun getServices(doctorId: Int) {
        launch {
            serviceRepository.getServices(doctorId).collect { serviceList ->
                serviceListCashed = serviceList.data
                _servicesTypes.value = serviceList.data.map { it.type }.also {
                    it.firstOrNull()?.let(::sortServiceList)
                }
            }
        }
    }

    fun sortServiceList(type: String) {
        launch {
            val data = serviceListCashed.filter { it.type == type }
            _services.value = ViewServiceList(data)
        }
    }
}