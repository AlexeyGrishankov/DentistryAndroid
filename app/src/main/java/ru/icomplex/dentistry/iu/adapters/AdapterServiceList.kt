package ru.icomplex.dentistry.iu.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.HolderServiceBinding
import ru.icomplex.dentistry.iu.adapters.EventClickState.OPEN
import ru.icomplex.dentistry.iu.adapters.EventClickType.SHORT
import ru.icomplex.dentistry.model.service.ViewService
import java.text.NumberFormat

class AdapterServiceList : RecyclerView.Adapter<AdapterServiceList.ServiceListHolder>() {

    private var list: MutableList<ViewService> = mutableListOf()
    private var eventClick: EventClick<ViewService>? = null

    fun updateList(newList: List<ViewService>) {
        val diffCallback = DiffUtilDoctorList(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setEventClick(event: EventClick<ViewService>) {
        eventClick = event
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.holder_service, parent, false)
        return ServiceListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ServiceListHolder, position: Int) {
        val service = list[position]
        with(holder.bind) {
            root.setOnClickListener {
                eventClick?.onPostClick(
                    EventClickAction(SHORT, OPEN, service, position)
                )
            }
            textFieldServiceName.text = service.name
            textFieldServicePrice.text = "${NumberFormat.getInstance().format(service.price)} ₽"
        }
    }

    override fun getItemCount() = list.size

    class ServiceListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bind by viewBinding(HolderServiceBinding::bind)
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
            return old.id == new.id && old.price == new.price && old.name == new.name
        }
    }
}