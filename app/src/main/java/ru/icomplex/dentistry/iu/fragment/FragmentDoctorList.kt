package ru.icomplex.dentistry.iu.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.FragmentDoctorListBinding
import ru.icomplex.dentistry.extension.changeVisible
import ru.icomplex.dentistry.extension.observe
import ru.icomplex.dentistry.iu.adapters.AdapterDoctorList
import ru.icomplex.dentistry.iu.adapters.EventClickState
import ru.icomplex.dentistry.iu.fragment.base.BaseFragment
import ru.icomplex.dentistry.iu.viewmodel.FragmentDoctorListViewModel
import ru.icomplex.dentistry.model.doctor.ViewDoctorList
import ru.icomplex.dentistry.model.notification.ViewNotificationList
import ru.icomplex.dentistry.model.settings.AppSettings
import javax.inject.Inject

@AndroidEntryPoint
class FragmentDoctorList : BaseFragment<FragmentDoctorListBinding>(
    R.layout.fragment_doctor_list,
    FragmentDoctorListBinding::bind,
) {

    companion object {
        const val DURATION_BTN_ANIMATION: Long = 500
    }

    @Inject
    lateinit var appSettings: AppSettings
    private val viewModel: FragmentDoctorListViewModel by viewModels()

    private val adapterDoctorList = AdapterDoctorList()

    private var badgeTextView: TextView? = null
    private var badgeIcon: View? = null

    override fun preInit() {
        setHasOptionsMenu(true)
    }

    override fun init(view: View, bundle: Bundle?) {
        setObservers()
        setButtons()
        setupDoctorList()
    }

    private fun setButtons() {
        bind.serviceSelectNextButton.setOnClickListener {
            adapterDoctorList.getSelected()?.let {
                findNavController().navigate(
                    FragmentDoctorListDirections
                        .actionFragmentDoctorListToFragmentSelectService(it.id)
                )
            }
        }
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
                findNavController().navigate(
                    FragmentDoctorListDirections
                        .actionFragmentDoctorListToFragmentNotificationList()
                )
            }
        }
    }

    private fun setObservers() {
        observe(viewModel.notification, ::setNotificationBadge)
        observe(viewModel.doctorList, ::updateDoctorList)
        viewModel.apply {
            getNotifications()
            getDoctors()
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

    private fun setupDoctorList() {
        bind.doctorList.adapter = adapterDoctorList.apply {
            setEventClick {
                if (it.state == EventClickState.SELECTED) {
                    selectedDoctor()
                } else if (it.state == EventClickState.UNSELECTED) {
                    unselectedDoctor()
                }
            }
        }
    }

    private fun selectedDoctor() {
        bind.constraintLayout.apply {
            animate()
                .translationYBy(measuredHeight.toFloat())
                .translationY(0f)
                .setDuration(DURATION_BTN_ANIMATION)
                .withStartAction { changeVisible(true) }
        }
    }

    private fun unselectedDoctor() {
        bind.constraintLayout.apply {
            animate()
                .translationY(measuredHeight.toFloat())
                .setDuration(DURATION_BTN_ANIMATION)
                .withEndAction { changeVisible(false) }
        }
    }

    override fun onStop() {
        super.onStop()
        adapterDoctorList.clear()
        unselectedDoctor()
    }

    private fun updateDoctorList(doctorsList: ViewDoctorList) {
        adapterDoctorList.updateList(doctorsList.data)
    }
}