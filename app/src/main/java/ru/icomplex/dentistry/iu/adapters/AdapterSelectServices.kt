package ru.icomplex.dentistry.iu.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.HolderSelectServiceBinding
import ru.icomplex.dentistry.extension.toFormat
import ru.icomplex.dentistry.iu.adapters.EventClickState.SELECTED
import ru.icomplex.dentistry.iu.adapters.EventClickState.UNSELECTED
import ru.icomplex.dentistry.iu.adapters.EventClickType.SHORT
import ru.icomplex.dentistry.model.service.ViewService

class AdapterSelectServices : RecyclerView.Adapter<AdapterSelectServices.ServiceSelectHolder>() {

    private var list: MutableList<ViewService> = mutableListOf()
    private var listSelected: MutableList<ViewService> = mutableListOf()
    private var eventClick: EventClick<ViewService>? = null

    fun updateList(newList: List<ViewService>) {
        listSelected.clear()
        val diffCallback = DiffUtilDoctorList(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setEventClick(event: EventClick<ViewService>) {
        eventClick = event
    }

    fun getSelectedItems(): List<ViewService> = listSelected

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceSelectHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.holder_select_service, parent, false)
        return ServiceSelectHolder(view)
    }

    private fun onPostClick(service: ViewService, position: Int, isSelected: Boolean) {
        if (!isSelected) {
            listSelected -= service
            eventClick?.onPostClick(EventClickAction(SHORT, UNSELECTED, service, position))
        } else {
            listSelected += service
            eventClick?.onPostClick(EventClickAction(SHORT, SELECTED, service, position))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ServiceSelectHolder, position: Int) {
        val service = list[position]
        with(holder.bind) {
            checkBox.setOnCheckedChangeListener {_, isSelected ->
                onPostClick(service, position, isSelected)
            }
            checkBox.isChecked = listSelected.contains(service)
            textFieldServiceNameSelectHolder.text = service.name
            textFieldServicePriceSelectHolder.text = "${service.price.toFormat()} ₽"
            textFieldServiceTimeSelectHolder.text = service.duration
        }
    }

    override fun getItemCount() = list.size

    class ServiceSelectHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bind by viewBinding(HolderSelectServiceBinding::bind)
    }

    class DiffUtilDoctorList(
        private val oldList: List<ViewService>,
        private val newList: List<ViewService>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]
            return old.id == new.id && old.price == new.price && old.name == new.name && old.duration == new.duration
        }
    }
}