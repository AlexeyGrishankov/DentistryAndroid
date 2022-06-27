package ru.icomplex.dentistry.iu.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.icomplex.dentistry.extension.collectLiveData
import ru.icomplex.dentistry.extension.launch
import ru.icomplex.dentistry.extension.toLiveData
import ru.icomplex.dentistry.model.notification.ViewNotificationList
import ru.icomplex.dentistry.model.service.ViewServiceList
import ru.icomplex.dentistry.repositories.notification.NotificationRepository
import ru.icomplex.dentistry.repositories.service.ServiceRepository
import javax.inject.Inject

@HiltViewModel
class FragmentServiceListViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val serviceRepository: ServiceRepository,
) : ViewModel() {

    private val _notifications = MutableLiveData<ViewNotificationList>()
    val notification = _notifications.toLiveData()

    private val _services = MutableLiveData<ViewServiceList>()
    val services = _services.toLiveData()

    fun getNotifications() {
        launch {
            notificationRepository.getNotifications().collectLiveData(_notifications)
        }
    }

    fun getServices() {
        launch {
            serviceRepository.getServices(null).collectLiveData(_services)
        }
    }
}