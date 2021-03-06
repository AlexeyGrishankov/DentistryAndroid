package ru.icomplex.dentistry.iu.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
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
import ru.icomplex.dentistry.extension.drawable
import ru.icomplex.dentistry.iu.adapters.EventClickState.SELECTED
import ru.icomplex.dentistry.iu.adapters.EventClickType.SHORT
import ru.icomplex.dentistry.model.doctor.ViewDoctor

class AdapterDoctorList : RecyclerView.Adapter<AdapterDoctorList.DoctorListHolder>() {

    private var list: MutableList<ViewDoctor> = mutableListOf()
    private var eventClick: EventClick<ViewDoctor>? = null
    private var selectedDoctor: Int = -1

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

    @SuppressLint("NotifyDataSetChanged")
    fun setSelectedDoctor(position: Int) {
        selectedDoctor = position
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: DoctorListHolder, position: Int) {
        val doctor = list[position]
        with(holder.bind) {
            holderDoctorRoot.apply {
                background = when (selectedDoctor) {
                    position -> context.drawable(R.drawable.selector_holder_doctor_stroke)
                    else -> null
                }
                setOnClickListener {
                    eventClick?.onPostClick(
                        EventClickAction(SHORT, SELECTED, doctor, position)
                    )
                }
            }
            textFieldDoctorFullName.text = doctor.fullName
            textFieldDoctorJob.text = doctor.positions
            doctorListBtn1.text = "09:30"
            doctorListBtn2.text = "10:00"
            doctorListBtn3.text = "10:30"

            Glide.with(holder.itemView.context)
                .load(doctor.imgUrl)
                .transform(CenterCrop(), CircleCrop())
                .into(imageDoctorAvatar)

            val title = "?????????????????? ???????????? ???? "
            val date = "25 ??????????"

            val colorDate = Color.parseColor("#000000")


            val spannable = SpannableStringBuilder().apply {
                append(title)
                append(date)
                setSpan(
                    ForegroundColorSpan(colorDate),
                    title.length,
                    title.length + date.length,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }

            textFieldNextTime.text = spannable
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
            return old.id == new.id && old.price == new.price && old.fullName == new.fullName
        }
    }
}