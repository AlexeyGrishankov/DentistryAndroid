package ru.icomplex.dentistry.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.icomplex.dentistry.repositories.auth.AuthRepository
import ru.icomplex.dentistry.repositories.auth.AuthRepositoryImpl
import ru.icomplex.dentistry.repositories.doctor.DoctorRepository
import ru.icomplex.dentistry.repositories.doctor.DoctorRepositoryImpl
import ru.icomplex.dentistry.repositories.notification.NotificationRepository
import ru.icomplex.dentistry.repositories.notification.NotificationRepositoryImpl
import ru.icomplex.dentistry.repositories.service.ServiceRepository
import ru.icomplex.dentistry.repositories.service.ServiceRepositoryImpl

@[Module InstallIn(SingletonComponent::class)]
abstract class RepositoriesModule {

    @Binds
    abstract fun bindAuthRepo(
        repo: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindNotificationRepo(
        repo: NotificationRepositoryImpl
    ): NotificationRepository

    @Binds
    abstract fun bindDoctorRepo(
        repo: DoctorRepositoryImpl
    ): DoctorRepository

    @Binds
    abstract fun bindServiceRepo(
        repo: ServiceRepositoryImpl
    ): ServiceRepository
}