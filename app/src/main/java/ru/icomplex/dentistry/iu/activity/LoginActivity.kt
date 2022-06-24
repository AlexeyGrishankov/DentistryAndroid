package ru.icomplex.dentistry.iu.activity

import android.app.Notification
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
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
import ru.icomplex.dentistry.model.notification.NotificationSettings
import ru.icomplex.dentistry.model.notification.NotificationSettings.Companion.ID
import ru.icomplex.dentistry.model.settings.AppSettings
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    companion object {
        const val NOTIFY_ID = 1002
    }

    private val bind by viewBinding(ActivityLoginBinding::bind)
    private val viewModel: LoginActivityViewModel by viewModels()

    @Inject
    lateinit var appSettings: AppSettings

    @Inject
    lateinit var notificationSettings: NotificationSettings

    private var isCodeMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCodeMode = true
        start()
    }

    private fun start() {
        bind.topAppBar.apply {
            title = "Стоматология ProDent"
            subtitle = "г.Реутов, ул.Реутовских ополченцев, д.2"
        }
        setObservers()
        setButtons()
        setAutoStart()
    }

    private fun setAutoStart() {
        appSettings.getCurrentToken()?.let {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun setButtons() {
        with(bind) {
            sendSmsButton.setOnClickListener(::onPostClickSendSmsBtn)
            sendCodeAgainBtn.setOnClickListener(::onPostClickSendCodeAgainBtn)
        }
    }

    private fun onPostClickSendCodeAgainBtn(_null: View) {
        sendSms()
    }

    private fun onPostClickSendSmsBtn(view: View) {
        with(bind) {
            when (isCodeMode) {
                false -> {
                    val auth = AuthCode(codeFieldLogin.getText(), phoneFieldLogin.getText())
                    viewModel.signIn(auth)
                }
                true -> {
                    isCodeMode = false
                    sendSms()
                    sendSmsButton.text = "Подтвердить"
                }
            }
        }
    }

    private fun sendSms() {
        with(bind) {
            sendCodeAgainBtn.changeVisible(false)
            viewModel.sendSMS(
                AuthPhone(phoneFieldLogin.getText())
            )
            object : CountDownTimer(
                TimeUnit.SECONDS.toMillis(5),
                TimeUnit.SECONDS.toMillis(1)
            ) {
                override fun onTick(millisUntilFinished: Long) {
                    val time = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                    val formatTime = when {
                        time < 10 -> "00:0$time"
                        else -> "00:$time"
                    }
                    textAgainSendCodeTimer.text = "Повторный запрос кода через: $formatTime"
                }

                override fun onFinish() {
                    sendCodeAgainBtn.changeVisible(true)
                }
            }.start()
        }
    }

    private fun setObservers() {
        observe(viewModel.code, ::getSms)
        observe(viewModel.auth, ::getAuth)
        observe(viewModel.progress, ::changeProgress)
    }

    private fun getSms(authCode: AuthCode) {
        val notification = NotificationCompat.Builder(this, ID)
            .setContentTitle("Авторизация")
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentText("Код подтверждения: ${authCode.code}")
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(true)
            .setTicker("Notification")
            .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
            .build()

        notificationSettings.getNotificationManager().notify(NOTIFY_ID, notification)
    }

    private fun getAuth(authGrant: AuthGrant) {
        toast("Успешная авторизация $authGrant")
        startMainActivity()
        finish()
    }

    private fun changeProgress(progress: Boolean) {
        bind.progressLogin.changeVisible(progress)
    }
}