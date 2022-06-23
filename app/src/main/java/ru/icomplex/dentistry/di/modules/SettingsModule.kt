package ru.icomplex.dentistry.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.icomplex.dentistry.model.notification.NotificationSettings
import ru.icomplex.dentistry.model.settings.AppSettings
import ru.icomplex.dentistry.model.settings.SharedPreferencesAppSettings

@[Module InstallIn(SingletonComponent::class)]
abstract class SettingsModule {

    @Binds
    abstract fun bindAppSettings(
        appSettings: SharedPreferencesAppSettings
    ): AppSettings
}
