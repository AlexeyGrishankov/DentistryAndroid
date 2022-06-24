package ru.icomplex.dentistry.iu.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.HolderDoctorBinding
import ru.icomplex.dentistry.iu.adapters.EventClickState.OPEN
import ru.icomplex.dentistry.iu.adapters.EventClickType.SHORT
import ru.icomplex.dentistry.model.doctor.ViewDoctor

class AdapterDoctorList : RecyclerView.Adapter<AdapterDoctorList.DoctorListHolder>() {

    private var list: MutableList<ViewDoctor> = mutableListOf()
    private var eventClick: EventClick<ViewDoctor>? = null

    fun updateList(newList: List<ViewDoctor>) {
        val diffCallback = DiffUtilDoctorList(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setEventClick(event: EventClick<ViewDoctor>) {
        eventClick = event
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.holder_doctor, parent, false)
        return DoctorListHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorListHolder, position: Int) {
        val doctor = list[position]
        with(holder.bind) {
            root.setOnClickListener {
                eventClick?.onPostClick(
                    EventClickAction(SHORT, OPEN, doctor, position)
                )
            }
            textFieldDoctorFullName.text = doctor.fullName
            textFieldDoctorJob.text = doctor.positions
            textFieldPrice.text = String.format("от %d ₽", doctor.price)
            Glide.with(holder.itemView.context)
                .load(doctor.imgUrl)
                .transform(CenterCrop(), CircleCrop())
                .into(imageDoctorAvatar)
        }
    }

    override fun getItemCount() = list.size

    class DoctorListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bind by viewBinding(HolderDoctorBinding::bind)
    }

    class DiffUtilDoctorList(
        private val oldList: List<ViewDoctor>,
        private val newList: List<ViewDoctor>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]
            return  old.id == new.id && old.price == new.price && old.fullName == new.fullName
        }
    }
}