package ru.icomplex.dentistry.extension

import android.content.Context
import android.widget.Toast

fun Context.toast(msg: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}