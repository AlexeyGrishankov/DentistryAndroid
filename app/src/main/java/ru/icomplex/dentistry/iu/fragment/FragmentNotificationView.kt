package ru.icomplex.dentistry.iu.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.FragmentNotificationViewBinding
import ru.icomplex.dentistry.extension.changeVisible
import ru.icomplex.dentistry.extension.getActionBar
import ru.icomplex.dentistry.iu.fragment.base.BaseFragment
import ru.icomplex.dentistry.model.settings.AppSettings
import javax.inject.Inject

@AndroidEntryPoint
class FragmentNotificationView : BaseFragment<FragmentNotificationViewBinding>(
    R.layout.fragment_notification_view,
    FragmentNotificationViewBinding::bind
) {

    private val args by navArgs<FragmentNotificationViewArgs>()

    @Inject
    lateinit var appSettings: AppSettings

    override fun onDestroy() {
        getActionBar { it.subtitle = "" }
        super.onDestroy()
    }

    override fun init(view: View, bundle: Bundle?) {
        with(bind) {
            args.notification.also { notify ->
                getActionBar { it.subtitle = notify.date }
                textFieldNotificationViewBody.text = notify.body
                textFieldNotificationViewHeader.text = notify.header
                textFieldNotificationViewWatched.changeVisible(true)
                appSettings.addNotificationsId(notify.id)
            }
        }
    }
}