package ru.icomplex.dentistry.extension

import android.content.res.Resources
import java.text.NumberFormat

/**
 * Функция расширения для Int. Конвертация в DP
 */
val Int.dp: Int
    inline get() = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Number.toFormat(): String =
    NumberFormat.getInstance().format(this)