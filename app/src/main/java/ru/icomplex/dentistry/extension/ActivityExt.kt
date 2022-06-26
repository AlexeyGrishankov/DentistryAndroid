package ru.icomplex.dentistry.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData

/**
 * Функция расширения для объявления наблюдателей
 *
 * @param[liveData] liveData
 * @param[doAction] выполняемое действие
 */
inline fun <T> AppCompatActivity.observe(liveData: LiveData<T>, crossinline doAction: (T) -> Unit) {
    liveData.observe(this) { doAction(it) }
}