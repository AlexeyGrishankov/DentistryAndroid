package ru.icomplex.dentistry.extension

import com.google.android.material.textfield.TextInputLayout

/**
 * Функция расширения получения текста для [TextInputLayout]
 *
 * @return текст
 */
fun TextInputLayout.getText(): String {
    return editText?.text?.toString() ?: ""
}

/**
 * Функция расширения установка текста для [TextInputLayout]
 *
 * @param[text] текст
 */
fun TextInputLayout.setText(text: String) {
    editText?.text?.apply {
        clear()
        insert(0, text)
    }
}