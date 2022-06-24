package ru.icomplex.dentistry.iu.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.icomplex.dentistry.extension.collectLiveData
import ru.icomplex.dentistry.extension.launch
import ru.icomplex.dentistry.extension.toLiveData
import ru.icomplex.dentistry.model.doctor.ViewDoctorList
import ru.icomplex.dentistry.model.notification.NotificationList
import ru.icomplex.dentistry.repositories.doctor.DoctorRepository
import ru.icomplex.dentistry.repositories.notification.NotificationRepository
import javax.inject.Inject

@HiltViewModel
class FragmentDoctorListViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val doctorRepository: DoctorRepository
) : ViewModel() {

    private val _notifications = MutableLiveData<NotificationList>()
    val notification = _notifications.toLiveData()

    private val _doctorList = MutableLiveData<ViewDoctorList>()
    val doctorList = _doctorList.toLiveData()

    fun getNotifications() {
        launch {
            notificationRepository.getNotifications().collectLiveData(_notifications)
        }
    }

    fun getDoctors() {
        launch {
            doctorRepository.getDoctors().collectLiveData(_doctorList)
        }
    }
}