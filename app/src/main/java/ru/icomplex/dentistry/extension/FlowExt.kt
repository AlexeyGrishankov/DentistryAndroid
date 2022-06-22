package ru.icomplex.dentistry.extension

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

suspend fun <T> Flow<T>.collectLiveData(liveData: MutableLiveData<T>) {
    collect { liveData.value = it }
}