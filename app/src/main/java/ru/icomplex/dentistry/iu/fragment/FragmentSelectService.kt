package ru.icomplex.dentistry.iu.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.FragmentSelectServiceBinding
import ru.icomplex.dentistry.extension.appActivity
import ru.icomplex.dentistry.extension.changeVisible
import ru.icomplex.dentistry.extension.observe
import ru.icomplex.dentistry.iu.adapters.AdapterSelectServices
import ru.icomplex.dentistry.iu.adapters.AdapterTypeOfServices
import ru.icomplex.dentistry.iu.adapters.EventClickAction
import ru.icomplex.dentistry.iu.adapters.EventClickState
import ru.icomplex.dentistry.iu.fragment.base.BaseFragment
import ru.icomplex.dentistry.iu.viewmodel.FragmentSelectServiceViewModel
import ru.icomplex.dentistry.model.service.ViewService
import ru.icomplex.dentistry.model.service.ViewServiceList

@AndroidEntryPoint
class FragmentSelectService : BaseFragment<FragmentSelectServiceBinding>(
    R.layout.fragment_select_service,
    FragmentSelectServiceBinding::bind,
) {

    companion object {
        const val DURATION_BTN_ANIMATION: Long = 500
    }

    private val args by navArgs<FragmentSelectServiceArgs>()


    private val viewModel: FragmentSelectServiceViewModel by viewModels()

    private val adapterServiceList = AdapterSelectServices()
    private val adapterTypeOfServices = AdapterTypeOfServices()

    override fun preInit() {
        setHasOptionsMenu(true)
        appActivity.supportActionBar?.subtitle = "Выберите услугу"
    }

    override fun init(view: View, bundle: Bundle?) {
        setObservers()
        setupAdapters()
        bind.serviceSelectNextButton.setOnClickListener {
            findNavController().navigate(
                FragmentSelectServiceDirections
                    .actionFragmentSelectServiceToFragmentSelectDate()
            )
        }
    }

    private fun setupAdapters() {
        with(bind) {
            recyclerTypeServices.adapter = adapterTypeOfServices.apply {
                setEventClick(::onPostSelectServiceType)
            }
            recyclerServices.adapter = adapterServiceList.apply {
                setEventClick(::onPostSelectService)
            }
        }
    }

    private fun onPostSelectService(event: EventClickAction<ViewService>) {
        adapterServiceList.getSelectedItems().let { items ->
            if (event.state == EventClickState.UNSELECTED) {
                if (items.isEmpty()) {
                    hideButtonNext()
                }
            } else if (event.state == EventClickState.SELECTED) {
                if (items.isNotEmpty()) {
                    showButtonNext()
                }
            }
        }
    }

    private fun showButtonNext() {
        bind.constraintLayout.apply {
            animate().translationYBy(measuredHeight.toFloat()).translationY(0f)
                .setDuration(DURATION_BTN_ANIMATION).withStartAction { changeVisible(true) }
        }
    }

    private fun hideButtonNext() {
        bind.constraintLayout.apply {
            animate().translationY(measuredHeight.toFloat())
                .setDuration(FragmentDoctorList.DURATION_BTN_ANIMATION)
                .withEndAction { changeVisible(false) }
        }
    }

    private fun onPostSelectServiceType(event: EventClickAction<String>) {
        viewModel.sortServiceList(event.item)
    }

    private fun setObservers() {
        observe(viewModel.servicesTypes, ::updateTypes)
        observe(viewModel.services, ::updateServiceList)
        viewModel.getServices(args.doctorId)
    }

    private fun updateServiceList(viewServiceList: ViewServiceList) {
        adapterServiceList.updateList(viewServiceList.data)
        hideButtonNext()
    }

    private fun updateTypes(list: List<String>) {
        adapterTypeOfServices.updateList(list)
    }
}