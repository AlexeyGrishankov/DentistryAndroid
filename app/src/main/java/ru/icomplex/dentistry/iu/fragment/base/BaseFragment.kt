package ru.icomplex.dentistry.iu.fragment.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.viewBinding

abstract class BaseFragment<BIND : ViewBinding>(
    @LayoutRes layoutID: Int,
    bind: (View) -> BIND
) : Fragment(layoutID) {
    protected val bind by viewBinding(bind)

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)
        preInit()
        init(view, bundle)
        postInit()
    }

    open fun preInit() {}

    abstract fun init(view: View, bundle: Bundle?)

    open fun postInit() {}
}