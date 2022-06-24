package ru.icomplex.dentistry.extension

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.icomplex.dentistry.iu.adapters.ScrollShowHideListener

/**
 * Функция расширения слушатель скрола если первый элемент в зоне видимости
 */
inline fun RecyclerView.onScrollIfFirstItemVisible(crossinline action: (Boolean) -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val isVisible = (layoutManager as LinearLayoutManager)
                .findFirstCompletelyVisibleItemPosition() == 0
            action(isVisible)
        }
    })
}

/**
 * Функция расширения слушатель скрола для скрытия или отображения
 */
inline fun RecyclerView.onScrollShowHide(
    crossinline onShow: (Boolean) -> Unit,
) {
    setOnScrollChangeListener(object : ScrollShowHideListener() {
        override fun show() {
            onShow(true)
        }

        override fun hide() {
            onShow(false)
        }
    })
}