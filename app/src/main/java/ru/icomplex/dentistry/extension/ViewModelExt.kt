package ru.icomplex.dentistry.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

inline fun ViewModel.launch(
    progressLiveData: MutableLiveData<Boolean>? = null,
    context: CoroutineContext = EmptyCoroutineContext,
    crossinline block: suspend () -> Unit
) {
    progressLiveData?.value = true
    viewModelScope.launch(context) { block() }.invokeOnCompletion {
        progressLiveData?.value = false
    }
}

inline fun ViewModel.changeState(delay: Long = 0, crossinline action: suspend () -> Unit) {
    viewModelScope.launch { if (delay > 0) delay(delay); action() }
}


fun <T> MutableLiveData<T>.toLiveData(): LiveData<T> = this