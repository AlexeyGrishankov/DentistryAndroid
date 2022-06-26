package ru.icomplex.dentistry

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Приложение, является главным классом приложения
 */
@HiltAndroidApp
class App : Application() {

//    override fun onCreate() {
//        super.onCreate()
//        FirebaseMessaging.getInstance().subscribeToTopic("dentistry")
//            .addOnCompleteListener { task ->
//                var msg = "Subscribed"
//                if (!task.isSuccessful) {
//                    msg = "Subscribe failed"
//                }
//                Log.d(this.packageName, msg)
//                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//            }
//
//        FirebaseMessaging.getInstance().unsubscribeFromTopic("dentistry")
//    }

}