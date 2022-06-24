package ru.icomplex.dentistry.extension

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

/**
 * Функция расширения для объявления наблюдателей
 *
 * @param[liveData] liveData
 * @param[doAction] выполняемое действие
 */
inline fun <T> Fragment.observe(liveData: LiveData<T>, crossinline doAction: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner) { doAction(it) }
}

fun Fragment.toast(msg: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), msg, duration).show()
}

fun Fragment.context(): Context {
    return requireContext()
}

val Fragment.appActivity
    get() = requireActivity() as AppCompatActivity

inline fun Fragment.getActionBar(crossinline supportActionBar: (ActionBar) -> Unit) {
    appActivity.supportActionBar?.let(supportActionBar)
}