package ru.icomplex.dentistry.iu.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.icomplex.dentistry.extension.collectLiveData
import ru.icomplex.dentistry.extension.launch
import ru.icomplex.dentistry.extension.toLiveData
import ru.icomplex.dentistry.model.notification.ViewNotificationList
import ru.icomplex.dentistry.repositories.notification.NotificationRepository
import javax.inject.Inject

@HiltViewModel
class FragmentNotificationListViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
) : ViewModel() {

    private val _notifications = MutableLiveData<ViewNotificationList>()
    val notification = _notifications.toLiveData()

    fun getNotifications() {
        launch {
            notificationRepository.getNotifications().collectLiveData(_notifications)
        }
    }
}