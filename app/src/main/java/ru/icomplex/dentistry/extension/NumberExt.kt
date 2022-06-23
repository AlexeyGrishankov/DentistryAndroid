package ru.icomplex.dentistry.extension

import android.content.res.Resources

/**
 * Функция расширения для Int. Конвертация в DP
 */
val Int.dp: Int
    inline get() = (this / Resources.getSystem().displayMetrics.density).toInt()
