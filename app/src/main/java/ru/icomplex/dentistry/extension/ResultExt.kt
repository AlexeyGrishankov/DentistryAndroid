package ru.icomplex.dentistry.extension

suspend inline fun <T> Result<T>.get(crossinline item: suspend (T) -> Unit) {
    getOrNull()?.let { item(it) }
}
