package ru.icomplex.dentistry.iu.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.HolderNotificationBinding
import ru.icomplex.dentistry.extension.changeVisible
import ru.icomplex.dentistry.iu.adapters.EventClickState.OPEN
import ru.icomplex.dentistry.iu.adapters.EventClickType.SHORT
import ru.icomplex.dentistry.model.notification.ViewNotification
import ru.icomplex.dentistry.model.settings.AppSettings

class AdapterNotificationList :
    RecyclerView.Adapter<AdapterNotificationList.NotificationListHolder>() {

    private var list: MutableList<ViewNotification> = mutableListOf()
    private var eventClick: EventClick<ViewNotification>? = null
    private var appSettings: AppSettings? = null

    fun updateList(newList: List<ViewNotification>) {
        val diffCallback = DiffUtilDoctorList(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setEventClick(event: EventClick<ViewNotification>) {
        eventClick = event
    }

    fun setAppSettings(appSettings: AppSettings) {
        this.appSettings = appSettings
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.holder_notification, parent, false)
        return NotificationListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NotificationListHolder, position: Int) {
        val notification = list[position]
        with(holder.bind) {
            root.setOnClickListener {
                eventClick?.onPostClick(
                    EventClickAction(SHORT, OPEN, notification, position)
                )
            }
            notificationWatched.changeVisible(
                appSettings?.getNotificationsIds()
                    ?.find { it == notification.id }
                    ?.let { true } ?: false
            )
            textFieldNotificationDate.text = notification.date
            textFieldNotificationHeader.text = notification.header
            textFieldNotificationBody.text = notification.body
        }
    }

    override fun getItemCount() = list.size

    class NotificationListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bind by viewBinding(HolderNotificationBinding::bind)
    }

    class DiffUtilDoctorList(
        private val oldList: List<ViewNotification>,
        private val newList: List<ViewNotification>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]
            return old.id == new.id
        }
    }
}