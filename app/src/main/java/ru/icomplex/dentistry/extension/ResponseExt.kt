package ru.icomplex.dentistry.extension

import retrofit2.Response

/**
 * Функция расширения получить из тела ответа результат
 */
fun <T> Response<T>.toResult(): Result<T> {
    return if (isSuccessful) {
        body()?.let {
            Result.success(it)
        } ?: Result.failure(Throwable(message()))
    } else {
        Result.failure(Throwable(message()))
    }
}
