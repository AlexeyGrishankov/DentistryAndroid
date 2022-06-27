package ru.icomplex.dentistry.iu.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.HolderDayCalendarBinding
import ru.icomplex.dentistry.extension.drawable
import ru.icomplex.dentistry.iu.adapters.EventClickState.SELECTED
import ru.icomplex.dentistry.iu.adapters.EventClickType.SHORT
import java.time.LocalDate
import java.time.YearMonth

typealias Date = LocalDate

const val ONE_MONTH: Long = 1

class AdapterCalendar : RecyclerView.Adapter<AdapterCalendar.DayHolder>() {

    companion object {
        const val OLD_MONTH_ID = 1999
        const val SHIFT_STEP = 1
    }

    private var calendar: MutableList<Date> = mutableListOf()
    private var eventClick: EventClick<Date>? = null
    private var selectedDate: Date = Date.now()

    var currentYearMonth: YearMonth = YearMonth.now()
        private set

    @SuppressLint("NotifyDataSetChanged")
    fun setDate(currentMonth: YearMonth) {
        calendar.clear()
        val year = currentMonth.year
        val month = currentMonth.month
        currentYearMonth = currentMonth
        val firstDayOfMonth = Date.of(year, month, SHIFT_STEP)
        val dayOfWeek = firstDayOfMonth.dayOfWeek

        repeat(dayOfWeek.ordinal) {
            calendar += Date.of(OLD_MONTH_ID, SHIFT_STEP, SHIFT_STEP)
        }

        repeat(currentMonth.lengthOfMonth()) {
            Date.of(year, month, it + SHIFT_STEP).also { date ->
                calendar += date
            }
        }
        notifyDataSetChanged()
    }

    inline fun previsionMonth(crossinline month: (YearMonth) -> Unit) {
        val newMonth = currentYearMonth.minusMonths(ONE_MONTH)
        setDate(newMonth)
        month(newMonth)
    }

    inline fun nextMonth(crossinline month: (YearMonth) -> Unit) {
        val newMonth = currentYearMonth.plusMonths(ONE_MONTH)
        setDate(newMonth)
        month(newMonth)
    }

    fun setEventClick(event: EventClick<Date>) {
        eventClick = event
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.holder_day_calendar, parent, false)
        return DayHolder(view)
    }

    private val Date.yearMonth: YearMonth
        get() = YearMonth.of(year, month)

    private fun bindSelectedDay(textView: TextView, context: Context, date: Date, position: Int) {
        textView.apply {
            isEnabled = true
            setTextColor(Color.WHITE)
            background = context.drawable(R.drawable.selector_type_of_service_selected)
            eventClick?.onPostClick(EventClickAction(SHORT, SELECTED, date, position))
        }
    }

    private fun bindOldDay(textView: TextView) {
        textView.apply {
            setTextColor(Color.GRAY)
            background = null
            isEnabled = false
        }
    }

    private fun bindToday(textView: TextView, context: Context, date: Date, position: Int) {
        textView.apply {
            isEnabled = true
            setTextColor(Color.BLACK)
            background = context.drawable(R.drawable.selector_type_of_service_default)
            setOnClickListener {
                eventClick?.onPostClick(EventClickAction(SHORT, SELECTED, date, position))
                val oldSelected = calendar.indexOf(selectedDate)
                selectedDate = date
                notifyItemChanged(oldSelected)
                notifyItemChanged(position)
            }
        }
    }

    private fun bindDefaultDay(textView: TextView, date: Date, position: Int) {
        textView.apply {
            isEnabled = true
            setTextColor(Color.BLACK)
            background = null
            setOnClickListener {
                eventClick?.onPostClick(EventClickAction(SHORT, SELECTED, date, position))
                val oldSelected = calendar.indexOf(selectedDate)
                selectedDate = date
                notifyItemChanged(oldSelected)
                notifyItemChanged(position)
            }
        }
    }

    private fun bindNoValidDay(textView: TextView) {
        textView.apply {
            setTextColor(Color.TRANSPARENT)
            background = null
        }
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        val context = holder.itemView.context
        val nowDate = Date.now()
        val date = calendar[position]
        with(holder.bind) {
            day.apply {
                text = date.dayOfMonth.toString()
                setOnClickListener(null)
            }
            when (date.yearMonth) {
                currentYearMonth -> when {
                    selectedDate == date -> bindSelectedDay(day, context, date, position)
                    date.isBefore(nowDate) -> bindOldDay(day)
                    date.isEqual(nowDate) -> bindToday(day, context, date, position)
                    else -> bindDefaultDay(day, date, position)
                }
                else -> bindNoValidDay(day)
            }
        }
    }

    override fun getItemCount() = calendar.size

    class DayHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bind by viewBinding(HolderDayCalendarBinding::bind)
    }
}