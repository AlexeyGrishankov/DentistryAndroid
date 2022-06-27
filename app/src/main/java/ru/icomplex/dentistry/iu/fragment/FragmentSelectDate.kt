package ru.icomplex.dentistry.iu.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.FragmentSelectDatetimeBinding
import ru.icomplex.dentistry.extension.appActivity
import ru.icomplex.dentistry.extension.changeVisible
import ru.icomplex.dentistry.extension.format
import ru.icomplex.dentistry.extension.toast
import ru.icomplex.dentistry.iu.adapters.*
import ru.icomplex.dentistry.iu.fragment.base.BaseFragment
import java.time.YearMonth
import java.time.format.DateTimeFormatter.ofPattern

@AndroidEntryPoint
class FragmentSelectDate : BaseFragment<FragmentSelectDatetimeBinding>(
    R.layout.fragment_select_datetime,
    FragmentSelectDatetimeBinding::bind,
) {

    companion object {
        private const val DURATION_BTN_ANIMATION: Long = 500
        private const val FORMAT_DATE_YEAR_MONTH = "MMMM yyyy"
        private const val FORMAT_FULL_DATE = "dd.MM.yy HH:mm"
        private val FORMATTER_DATE_YEAR_MONTH = ofPattern(FORMAT_DATE_YEAR_MONTH)
    }

    private val calendarAdapter = AdapterCalendar()
    private val timeRangeAdapter = AdapterTimeRange()

    override fun preInit() {
        appActivity.supportActionBar?.subtitle = "Выберите время и дату"
    }

    override fun init(view: View, bundle: Bundle?) {
        setupAdapters()
        bind.dateSelectNextButton.setOnClickListener {
            timeRangeAdapter.selectedTime?.let {
                val dateString = it.format(FORMAT_FULL_DATE)
                toast("Дата выбрана: $dateString")
            }
        }
    }

    private fun setupAdapters() {
        with(bind) {
            recyclerDayOfWeek.apply {
                adapter = AdapterCalendarDayOfWeek()
                layoutManager = object : GridLayoutManager(requireContext(), 7) {
                    override fun canScrollVertically(): Boolean {
                        return false
                    }
                }
            }
            recyclerCalendar.apply {
                layoutManager = object : GridLayoutManager(requireContext(), 7) {
                    override fun canScrollVertically(): Boolean {
                        return false
                    }
                }
                adapter = calendarAdapter.apply {
                    YearMonth.now().let {
                        setDate(it)
                        monthAndYear.text = it.format(FORMATTER_DATE_YEAR_MONTH)
                    }
                    setEventClick(::onPostClickCalendarDay)
                    prevMonth.setOnClickListener {
                        previsionMonth { month ->
                            monthAndYear.text = month.format(FORMATTER_DATE_YEAR_MONTH)
                        }
                    }
                    nextMonth.setOnClickListener {
                        nextMonth { month ->
                            monthAndYear.text = month.format(FORMATTER_DATE_YEAR_MONTH)
                        }
                    }
                }
            }
            recyclerTimeRange.apply {
                layoutManager = object : GridLayoutManager(requireContext(), 5) {
                    override fun canScrollVertically(): Boolean {
                        return false
                    }
                }
                adapter = timeRangeAdapter.apply {
                    setEventClick(::onPostClickTime)
                }
            }
        }
    }

    private fun onPostClickTime(event: EventClickAction<DateTime?>) {
        if (event.state == EventClickState.SELECTED) {
            showButtonNext()
        } else {
            hideButtonNext()
        }
    }

    private fun onPostClickCalendarDay(event: EventClickAction<Date>) {
        if (event.state == EventClickState.SELECTED) {
            var startTime = event.item.let {
                DateTime.of(it.year, it.month, it.dayOfMonth, 9, 0)
            }

            //DEBUG START
            val list = ArrayList<DateTime>()
            repeat(12) {
                list += startTime.plusMinutes(30).also { startTime = it }
            }
            updateTimeRange(list)
            //DEBUG END

        } else {
            updateTimeRange(emptyList())
        }
    }

    private fun updateTimeRange(list: List<DateTime>) {
        timeRangeAdapter.updateList(list)
    }

    private fun showButtonNext() {
        bind.constraintLayout.apply {
            animate().translationYBy(measuredHeight.toFloat()).translationY(0f)
                .setDuration(DURATION_BTN_ANIMATION).withStartAction { changeVisible(true) }
        }
    }

    private fun hideButtonNext() {
        bind.constraintLayout.apply {
            animate().translationY(measuredHeight.toFloat())
                .setDuration(FragmentDoctorList.DURATION_BTN_ANIMATION)
                .withEndAction { changeVisible(false) }
        }
    }

}