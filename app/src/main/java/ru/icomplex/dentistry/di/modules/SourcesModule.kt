package ru.icomplex.dentistry.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.icomplex.dentistry.sources.auth.AuthRetrofitSource
import ru.icomplex.dentistry.sources.auth.AuthSource
import ru.icomplex.dentistry.sources.doctor.DoctorRetrofitSource
import ru.icomplex.dentistry.sources.doctor.DoctorSource
import ru.icomplex.dentistry.sources.notification.NotificationRetrofitSource
import ru.icomplex.dentistry.sources.notification.NotificationSource
import ru.icomplex.dentistry.sources.service.ServiceRetrofitSource
import ru.icomplex.dentistry.sources.service.ServiceSource

@[Module InstallIn(SingletonComponent::class)]
abstract class SourcesModule {

    @Binds
    abstract fun bindAuthSource(
        source: AuthRetrofitSource
    ): AuthSource

    @Binds
    abstract fun bindNotificationSource(
        source: NotificationRetrofitSource
    ): NotificationSource

    @Binds
    abstract fun bindDoctorSource(
        source: DoctorRetrofitSource
    ): DoctorSource

    @Binds
    abstract fun bindServiceSource(
        source: ServiceRetrofitSource
    ): ServiceSource

}