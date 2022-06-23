package ru.icomplex.dentistry.iu.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.HolderDoctorBinding
import ru.icomplex.dentistry.extension.dp
import ru.icomplex.dentistry.model.ViewDoctor

class AdapterDoctorList(val context: Context) :
    RecyclerView.Adapter<AdapterDoctorList.DoctorListHolder>() {

    private val list: List<ViewDoctor> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorListHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.holder_doctor, parent, false)
        return DoctorListHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorListHolder, position: Int) {
        val doctor = list[position]
        with(holder.bind) {
            textFieldDoctorFullName.text = doctor.fullName
            textFieldDoctorJob.text = doctor.positions
            textFieldPrice.text = doctor.price
            Glide.with(context)
                .load(doctor.imgUrl)
                .transform(CenterCrop(), RoundedCorners(36.dp))
                .into(imageDoctorAvatar)
        }
    }

    override fun getItemCount() = list.size

    class DoctorListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bind by viewBinding(HolderDoctorBinding::bind)
    }
}