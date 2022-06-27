package ru.icomplex.dentistry.iu.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.HolderSelectTimeBinding
import ru.icomplex.dentistry.extension.drawable
import ru.icomplex.dentistry.extension.format
import ru.icomplex.dentistry.iu.adapters.EventClickState.SELECTED
import ru.icomplex.dentistry.iu.adapters.EventClickState.UNSELECTED
import ru.icomplex.dentistry.iu.adapters.EventClickType.SHORT
import java.time.LocalDateTime

typealias DateTime = LocalDateTime

class AdapterTimeRange : RecyclerView.Adapter<AdapterTimeRange.TimeRangeHolder>() {

    private var timeRange: MutableList<DateTime> = mutableListOf()
    private var event: EventClick<DateTime?>? = null
    var selectedTime: DateTime? = timeRange.firstOrNull()
        private set

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(new: List<DateTime>) {
        timeRange.clear()
        timeRange += new
        selectedTime = null
        event?.onPostClick(EventClickAction(SHORT, UNSELECTED, selectedTime, -1))
        notifyDataSetChanged()
    }


    fun setEventClick(event: EventClick<DateTime?>) {
        this.event = event
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeRangeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.holder_select_time, parent, false)
        return TimeRangeHolder(view)
    }

    override fun onBindViewHolder(holder: TimeRangeHolder, position: Int) {
        val time = timeRange[position]
        with(holder.bind) {
            textFieldStartTime.apply {
                setOnClickListener { onPostClick(time, position) }
                text = time.format("HH:mm")
                background = if (selectedTime == time) {
                    setTextColor(Color.WHITE)
                    holder.itemView.context.drawable(R.drawable.selector_type_of_service_selected)
                } else {
                    setTextColor(Color.BLACK)
                    null
                }
            }
        }
    }

    private fun onPostClick(time: DateTime, pos: Int) {
        val id = timeRange.indexOf(selectedTime)
        selectedTime = when (selectedTime) {
            time -> event?.onPostClick(EventClickAction(SHORT, UNSELECTED, time, pos)).let { null }
            else -> event?.onPostClick(EventClickAction(SHORT, SELECTED, time, pos)).let { time }
        }
        notifyItemChanged(id)
        notifyItemChanged(pos)
    }

    override fun getItemCount() = timeRange.size

    class TimeRangeHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bind by viewBinding(HolderSelectTimeBinding::bind)
    }
}