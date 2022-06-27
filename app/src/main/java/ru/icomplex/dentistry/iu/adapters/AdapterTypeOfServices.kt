package ru.icomplex.dentistry.iu.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.HolderTypeOfServiceBinding
import ru.icomplex.dentistry.extension.drawable
import ru.icomplex.dentistry.iu.adapters.EventClickState.SELECTED
import ru.icomplex.dentistry.iu.adapters.EventClickType.SHORT

class AdapterTypeOfServices : RecyclerView.Adapter<AdapterTypeOfServices.ServiceTypeHolder>() {

    private var list: MutableList<String> = mutableListOf()
    private var eventClick: EventClick<String>? = null
    private var selectedType: Int = 0

    fun updateList(newList: List<String>) {
        val diffCallback = DiffUtilDoctorList(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getSelectedType() = list[selectedType]

    fun setEventClick(event: EventClick<String>) {
        eventClick = event
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceTypeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.holder_type_of_service, parent, false)
        return ServiceTypeHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onPostClick(serviceType: String, position: Int) {
        eventClick?.onPostClick(EventClickAction(SHORT, SELECTED, serviceType, position))
        selectedType = position
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ServiceTypeHolder, position: Int) {
        val serviceType = list[position]
        with(holder.bind) {
            root.setOnClickListener { onPostClick(serviceType, holder.bindingAdapterPosition) }
            with(textFieldTypeServiceName) {
                background = if (selectedType == position) {
                    setTextColor(Color.WHITE)
                    holder.itemView.context.drawable(R.drawable.selector_type_of_service_selected)
                } else {
                    setTextColor(Color.BLACK)
                    holder.itemView.context.drawable(R.drawable.selector_type_of_service_default)
                }
                text = serviceType
            }
        }

    }

    override fun getItemCount() = list.size

    class ServiceTypeHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bind by viewBinding(HolderTypeOfServiceBinding::bind)
    }

    class DiffUtilDoctorList(
        private val oldList: List<String>,
        private val newList: List<String>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]
            return old === new
        }
    }
}