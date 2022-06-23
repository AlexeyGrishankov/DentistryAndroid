package ru.icomplex.dentistry.model

import java.io.Serializable

data class ViewDoctor(
    val id: Int,
    val fullName: String,
    val imgUrl: String,
    val positions: String,
    val price: String,
): Serializable
