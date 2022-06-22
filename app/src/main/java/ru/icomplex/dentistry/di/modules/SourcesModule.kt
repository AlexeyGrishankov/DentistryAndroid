package ru.icomplex.dentistry.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.icomplex.dentistry.sources.auth.AuthRetrofitSource
import ru.icomplex.dentistry.sources.auth.AuthSource
import ru.icomplex.dentistry.sources.notification.NotificationRetrofitSource
import ru.icomplex.dentistry.sources.notification.NotificationSource
import ru.icomplex.dentistry.sources.profile.ProfileRetrofitSource
import ru.icomplex.dentistry.sources.profile.ProfileSource

@[Module InstallIn(SingletonComponent::class)]
abstract class SourcesModule {

    @Binds
    abstract fun bindAuthSource(
        source: AuthRetrofitSource
    ): AuthSource

    @Binds
    abstract fun bindProfileSource(
        source: ProfileRetrofitSource
    ): ProfileSource

    @Binds
    abstract fun bindNotificationSource(
        source: NotificationRetrofitSource
    ): NotificationSource
}