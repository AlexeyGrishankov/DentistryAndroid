package ru.icomplex.dentistry.iu.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.FragmentServiceListBinding
import ru.icomplex.dentistry.extension.*
import ru.icomplex.dentistry.iu.adapters.AdapterServiceList
import ru.icomplex.dentistry.iu.fragment.base.BaseFragment
import ru.icomplex.dentistry.iu.viewmodel.FragmentServiceListViewModel
import ru.icomplex.dentistry.model.notification.ViewNotificationList
import ru.icomplex.dentistry.model.service.ViewServiceList
import ru.icomplex.dentistry.model.settings.AppSettings
import javax.inject.Inject

@AndroidEntryPoint
class FragmentServiceList : BaseFragment<FragmentServiceListBinding>(
    R.layout.fragment_service_list,
    FragmentServiceListBinding::bind,
) {

    @Inject
    lateinit var appSettings: AppSettings
    private val viewModel: FragmentServiceListViewModel by viewModels()
    private val adapterServiceList = AdapterServiceList()

    var badgeTextView: TextView? = null
    private var badgeIcon: View? = null

    override fun preInit() {
        setHasOptionsMenu(true)
    }

    override fun init(view: View, bundle: Bundle?) {
        setObservers()
        setupAdapterServiceList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_doctor_list_menu_toolbar, menu)

        val item = menu.findItem(R.id.notification_item)
        item.setActionView(R.layout.layout_notification_badge)
        val view = item.actionView
        badgeTextView = view.findViewById(R.id.count)
        badgeIcon = view.findViewById(R.id.badge)
        view.findViewById<View>(R.id.notificationBadgeRoot).apply {
            setOnClickListener {
                //todo
//                findNavController().navigate(
//                    FragmentServiceListDirections
//                        .actionFragmentServicesToFragmentNotificationList()
//                )
            }
        }
    }

    private fun setupAdapterServiceList() {
        with(bind.serviceList) {
            adapter = adapterServiceList.apply {
                setEventClick {
                    toast(it.item.name)
                }
            }
            addItemDecoration(DividerItemDecoration(context(), LinearLayoutManager.VERTICAL).apply {
                getDraw(R.drawable.selector_item_decorator_service_list)?.let { setDrawable(it) }
            })
        }
    }

    private fun setNotificationBadge(viewNotificationList: ViewNotificationList) {
        badgeTextView?.text = appSettings.getNotificationsIds().let { oldIds ->
            viewNotificationList.data.filter { !oldIds.contains(it.id) }.size.also {
                badgeIcon?.changeVisible(it != 0)
                badgeTextView?.changeVisible(it != 0)
            }.toString()
        }
    }

    private fun setObservers() {
        observe(viewModel.services, ::updateServiceList)
        observe(viewModel.notification, ::setNotificationBadge)
        viewModel.apply {
            getNotifications()
            getServices()
        }
    }

    private fun updateServiceList(viewServiceList: ViewServiceList) {
        adapterServiceList.updateList(viewServiceList.data)
    }
}