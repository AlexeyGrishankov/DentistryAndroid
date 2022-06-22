package ru.icomplex.dentistry.iu.activity

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.DEFAULT_SOUND
import androidx.core.app.NotificationCompat.DEFAULT_VIBRATE
import androidx.core.app.NotificationManagerCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.icomplex.dentistry.R
import ru.icomplex.dentistry.databinding.ActivityLoginBinding
import ru.icomplex.dentistry.extension.changeVisible
import ru.icomplex.dentistry.extension.getText
import ru.icomplex.dentistry.extension.observe
import ru.icomplex.dentistry.extension.toast
import ru.icomplex.dentistry.iu.viewmodel.LoginActivityViewModel
import ru.icomplex.dentistry.model.auth.AuthCode
import ru.icomplex.dentistry.model.auth.AuthGrant
import ru.icomplex.dentistry.model.auth.AuthPhone
import ru.icomplex.dentistry.model.settings.AppSettings
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    private val bind by viewBinding(ActivityLoginBinding::bind)
    private val viewModel: LoginActivityViewModel by viewModels()

    @Inject
    lateinit var appSettings: AppSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        bind.layoutCode.visibility = GONE
        setObservers()
        setButtons()
    }

    private fun setButtons() {
        with(bind) {
            sendSmsButton.setOnClickListener {
                viewModel.sendSMS(
                    AuthPhone(phoneFieldLogin.getText())
                )
                it.changeVisible(false)
                layoutCode.changeVisible(true)
            }
            sendCodeBtn.setOnClickListener {
                viewModel.signIn(
                    AuthCode(
                        codeFieldLogin.getText(),
                        phoneFieldLogin.getText()
                    )
                )
            }
        }
    }

    private fun setObservers() {
        observe(viewModel.code, ::getSms)
        observe(viewModel.auth, ::getAuth)
        observe(viewModel.progress, ::changeProgress)
    }


    private fun getSms(authCode: AuthCode) {
        val name = "AuthCode"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("AUTH_SMS", name, importance).apply {
            setShowBadge(true)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(this, "AUTH_SMS")
            .setContentTitle("Авторизация")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentText("Код подтверждения: ${authCode.code}")
            .setDefaults(Notification.DEFAULT_ALL)
            .setDefaults(DEFAULT_SOUND or DEFAULT_VIBRATE)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)) {
            notify(0, builder.build())
        }
    }

    private fun getAuth(authGrant: AuthGrant) {
        toast("Успешная авторизация $authGrant")
    }

    private fun changeProgress(progress: Boolean) {
        bind.progressLogin.changeVisible(progress)
    }
}