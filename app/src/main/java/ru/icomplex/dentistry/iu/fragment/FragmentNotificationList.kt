package ru.icomplex.dentistry.iu.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.FragmentNotificationListBinding
import ru.icomplex.dentistry.extension.context
import ru.icomplex.dentistry.extension.getDraw
import ru.icomplex.dentistry.extension.observe
import ru.icomplex.dentistry.iu.adapters.AdapterNotificationList
import ru.icomplex.dentistry.iu.fragment.base.BaseFragment
import ru.icomplex.dentistry.iu.viewmodel.FragmentNotificationListViewModel
import ru.icomplex.dentistry.model.notification.ViewNotificationList
import ru.icomplex.dentistry.model.settings.AppSettings
import javax.inject.Inject

@AndroidEntryPoint
class FragmentNotificationList : BaseFragment<FragmentNotificationListBinding>(
    R.layout.fragment_notification_list,
    FragmentNotificationListBinding::bind
) {

    private val viewModel: FragmentNotificationListViewModel by viewModels()

    @Inject
    lateinit var appSettings: AppSettings
    private val adapterNotificationList = AdapterNotificationList()

    override fun init(view: View, bundle: Bundle?) {
        setObservers()
        setupAdapterNotificationList()
    }

    private fun setObservers() {
        observe(viewModel.notification, ::updateNotificationList)
        viewModel.getNotifications()
    }

    private fun updateNotificationList(viewNotificationList: ViewNotificationList) {
        adapterNotificationList.updateList(viewNotificationList.data)
    }

    private fun setupAdapterNotificationList() {
        with(bind.notificationList) {
            adapter = adapterNotificationList.apply {
                setEventClick {
                    findNavController().navigate(
                        FragmentNotificationListDirections
                            .actionFragmentNotificationListToFragmentNotificationView(it.item)
                    )
                }
                setAppSettings(appSettings)
            }
            addItemDecoration(DividerItemDecoration(context(), LinearLayoutManager.VERTICAL).apply {
                getDraw(R.drawable.selector_item_decorator_service_list)?.let { setDrawable(it) }
            })
        }
    }
}