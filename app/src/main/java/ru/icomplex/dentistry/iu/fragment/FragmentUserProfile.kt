package ru.icomplex.dentistry.iu.fragment

import android.os.Bundle
import android.view.View
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.FragmentUserProfileBinding
import ru.icomplex.dentistry.iu.fragment.base.BaseFragment

class FragmentUserProfile : BaseFragment<FragmentUserProfileBinding>(
    R.layout.fragment_user_profile,
    FragmentUserProfileBinding::bind,
) {

    override fun init(view: View, bundle: Bundle?) {

    }
}