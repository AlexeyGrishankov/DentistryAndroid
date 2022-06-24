package ru.icomplex.dentistry.iu.adapters

import android.view.View

/**
 * Слушатель для изменения состояние видимости при скролинге
 */
abstract class ScrollShowHideListener : View.OnScrollChangeListener {

    companion object {
        const val MINIMUM = 25f
        const val SCROLL_DIST_DEFAULT = 0
    }

    var scrollDist = 0
    var isVisible = true

    override fun onScrollChange(
        v: View?,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        onScrollChangeY(scrollY, oldScrollY)
    }

    /**
     *  Выполнить при изменении скролла по оси Y
     *  @param[scrollY] текущее состоянии скролла
     *  @param[oldScrollY] предыдущее состояние скролла
     */
    private fun onScrollChangeY(scrollY: Int, oldScrollY: Int) {
        if (isVisible && scrollDist > MINIMUM) {
            hide()
            scrollDist = SCROLL_DIST_DEFAULT
            isVisible = false
        } else if (!isVisible && scrollDist < -MINIMUM) {
            show()
            scrollDist = SCROLL_DIST_DEFAULT
            isVisible = true
        }

        if ((isVisible && scrollY > oldScrollY) || (!isVisible && scrollY < oldScrollY)) {
            scrollDist += scrollY - oldScrollY
        }
    }

    /**
     * Отобразить данные
     */
    abstract fun show()

    /**
     * Скрыть данные
     */
    abstract fun hide()
}