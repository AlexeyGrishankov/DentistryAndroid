package ru.icomplex.dentistry.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat

fun Context.toast(msg: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun Context.drawable(@DrawableRes id: Int): Drawable? {
    return ResourcesCompat.getDrawable(this.resources, id, null)
}