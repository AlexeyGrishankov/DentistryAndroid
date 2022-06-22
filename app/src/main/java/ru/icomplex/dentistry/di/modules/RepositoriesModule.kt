package ru.icomplex.dentistry.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.icomplex.dentistry.repositories.auth.AuthRepository
import ru.icomplex.dentistry.repositories.auth.AuthRepositoryImpl
import ru.icomplex.dentistry.repositories.notification.NotificationRepository
import ru.icomplex.dentistry.repositories.notification.NotificationRepositoryImpl
import ru.icomplex.dentistry.repositories.profile.ProfileRepository
import ru.icomplex.dentistry.repositories.profile.ProfileRepositoryImpl

@[Module InstallIn(SingletonComponent::class)]
abstract class RepositoriesModule {

    @Binds
    abstract fun bindAuthRepo(
        repo: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindProfileRepo(
        repo: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    abstract fun bindNotificationRepo(
        repo: NotificationRepositoryImpl
    ): NotificationRepository
}