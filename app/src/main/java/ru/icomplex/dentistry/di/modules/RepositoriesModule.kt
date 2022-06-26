package ru.icomplex.dentistry.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.icomplex.dentistry.repositories.auth.AuthRepository
import ru.icomplex.dentistry.repositories.auth.AuthRepositoryImpl
import ru.icomplex.dentistry.repositories.doctor.DoctorRepository
import ru.icomplex.dentistry.repositories.doctor.DoctorRepositoryImpl

@[Module InstallIn(SingletonComponent::class)]
abstract class RepositoriesModule {

    @Binds
    abstract fun bindAuthRepo(
        repo: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindDoctorRepo(
        repo: DoctorRepositoryImpl
    ): DoctorRepository
}