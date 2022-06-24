package ru.icomplex.dentistry.iu.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.FragmentDoctorListBinding
import ru.icomplex.dentistry.extension.*
import ru.icomplex.dentistry.iu.adapters.AdapterDoctorList
import ru.icomplex.dentistry.iu.adapters.EventClick
import ru.icomplex.dentistry.iu.fragment.base.BaseFragment
import ru.icomplex.dentistry.iu.viewmodel.FragmentDoctorListViewModel
import ru.icomplex.dentistry.model.doctor.ViewDoctorList
import ru.icomplex.dentistry.model.notification.NotificationList
import ru.icomplex.dentistry.model.settings.AppSettings
import javax.inject.Inject

@AndroidEntryPoint
class FragmentDoctorList : BaseFragment<FragmentDoctorListBinding>(
    R.layout.fragment_doctor_list,
    FragmentDoctorListBinding::bind,
) {

    @Inject
    lateinit var appSettings: AppSettings
    private val viewModel: FragmentDoctorListViewModel by viewModels()

    private val adapterDoctorList = AdapterDoctorList()

    var badgeTextView: TextView? = null
    var notificationMenuItem: View? = null

    override fun preInit() {
        setHasOptionsMenu(true)
    }

    override fun init(view: View, bundle: Bundle?) {
        setObservers()
        startAppBar()
        setupDoctorList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_doctor_list_menu_toolbar, menu)

        val item = menu.findItem(R.id.notification_item)
        item.setActionView(R.layout.aaaa)
        val view = item.actionView
        badgeTextView = view.findViewById(R.id.count)
        notificationMenuItem = view.findViewById(R.id.notificationBadgeRoot)
    }

    private fun setObservers() {
        viewModel.apply {
            getNotifications()
            getDoctors()
        }
        observe(viewModel.notification, ::setNotificationBadge)
        observe(viewModel.doctorList, ::updateDoctorList)
    }

    private fun startAppBar() {
        getActionBar { it.title = "Врачи" }
        notificationMenuItem?.apply {
            setOnClickListener { toast("asdasdsd") }
        }
    }

    private fun setNotificationBadge(notificationList: NotificationList) {
        badgeTextView?.text = appSettings.getNotificationsIds().let { oldIds ->
            notificationList.data.filter { !oldIds.contains(it.id) }.size.toString()
        }
    }

    private fun setupDoctorList() {
        bind.doctorList.apply {
            adapter = adapterDoctorList.apply {
                setEventClick {
                    toast(it.item.fullName)
                }
            }

        }
    }

    private fun updateDoctorList(doctorsList: ViewDoctorList) {
        adapterDoctorList.updateList(doctorsList.data)
    }
}