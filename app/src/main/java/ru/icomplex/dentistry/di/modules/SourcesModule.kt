package ru.icomplex.dentistry.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.icomplex.dentistry.sources.auth.AuthRetrofitSource
import ru.icomplex.dentistry.sources.auth.AuthSource

@[Module InstallIn(SingletonComponent::class)]
abstract class SourcesModule {

    @Binds
    abstract fun bindAuthSource(
        source: AuthRetrofitSource
    ): AuthSource

}