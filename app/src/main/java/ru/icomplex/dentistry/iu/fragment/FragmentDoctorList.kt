package ru.icomplex.dentistry.iu.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.FragmentDoctorListBinding
import ru.icomplex.dentistry.extension.context
import ru.icomplex.dentistry.iu.fragment.base.BaseFragment


class FragmentDoctorList : BaseFragment<FragmentDoctorListBinding>(
    R.layout.fragment_doctor_list,
    FragmentDoctorListBinding::bind,
) {

    override fun preInit() {
        setHasOptionsMenu(true)
    }

    override fun init(view: View, bundle: Bundle?) {
        startAppBar()
    }

    private fun startAppBar() {

    }

    var count: Int = 0

    @ExperimentalBadgeUtils
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_doctor_list_menu_toolbar, menu)

        val badge = BadgeDrawable.create(context()).apply {
            number = 50
            isVisible = true
        }
        BadgeUtils.attachBadgeDrawable(badge, bind.actionBarProfile, R.id.notification_item)

    }
}