package ru.icomplex.dentistry.iu.fragment

import android.os.Bundle
import android.view.View
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.FragmentServicesBinding
import ru.icomplex.dentistry.iu.fragment.base.BaseFragment

class FragmentServices : BaseFragment<FragmentServicesBinding>(
    R.layout.fragment_services,
    FragmentServicesBinding::bind,
) {

    override fun init(view: View, bundle: Bundle?) {

    }
}