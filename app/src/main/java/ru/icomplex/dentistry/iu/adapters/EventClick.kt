package ru.icomplex.dentistry.iu.adapters

/**
 * Event Clicker для обработки нажатий UI
 */
fun interface EventClick<T> {

    /**
     * Действие после нажатия
     * @param action действие
     */
    fun onPostClick(action: EventClickAction<T>)
}

/**
 * Кастомный обработчик нажатия для RecyclerView
 */
fun interface OnItemClickListener {
    /**
     * Вызов callback по клику на элемент
     *
     * @param[position] позиция элемента
     */
    fun onItemClick(position: Int)
}

/**
 * Состояние клика
 */
enum class EventClickState {
    OPEN, CREATE, EDIT, DELETE, EXPAND, COLLAPSE, SKIP, SELECTED;
}

/**
 * Тип клика
 */
enum class EventClickType {
    LONG, SHORT;
}

/**
 * Действие при обработки клика
 *
 * @param type тип клика
 * @param state состояние клика
 * @param item объект содержащийся в UI
 * @param pos позиция объекта в UI
 *
 */
data class EventClickAction<T>(
    val type: EventClickType,
    val state: EventClickState,
    val item: T,
    val pos: Int,
)

