package ru.icomplex.dentistry.iu.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.HolderDayCalendarBinding
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

class AdapterCalendarDayOfWeek : RecyclerView.Adapter<AdapterCalendarDayOfWeek.DayWeekHolder>() {

    private var calendar = DayOfWeek.values().toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayWeekHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.holder_day_calendar, parent, false)
        return DayWeekHolder(view)
    }

    override fun onBindViewHolder(holder: DayWeekHolder, position: Int) {
        val date = calendar[position]
        with(holder.bind) {
            day.apply {
                text = date.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault())
                setTextColor(holder.itemView.context.getColor(R.color.main))
            }
        }
    }

    override fun getItemCount() = calendar.size

    class DayWeekHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bind by viewBinding(HolderDayCalendarBinding::bind)
    }
}