package ru.icomplex.dentistry.repositories.profile

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.icomplex.dentistry.extension.get
import ru.icomplex.dentistry.model.profile.Profile
import ru.icomplex.dentistry.sources.profile.ProfileRetrofitSource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Репозиторий для авторизации по умолчанию
 *
 * @param retrofitProfileSource удаленный источник данных
 */
@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val retrofitProfileSource: ProfileRetrofitSource,
) : ProfileRepository {

    /**
     * Получить профиль пользователя
     */
    override suspend fun getProfile(): Flow<Profile> = flow {
        retrofitProfileSource.getProfile().get { emit(it) }
    }
}