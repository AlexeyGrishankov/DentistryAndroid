package ru.icomplex.dentistry.extension

import android.view.View
import android.view.View.*

fun View.changeVisible(isVisible: Boolean, isGone: Boolean = true) {
    visibility = if (isVisible) VISIBLE else if (isGone) GONE else INVISIBLE
}