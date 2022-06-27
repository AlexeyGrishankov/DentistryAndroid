package ru.icomplex.dentistry.extension

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

fun LocalDate.format(string: String): String {
    return this.format(DateTimeFormatter.ofPattern(string))
}

fun LocalDateTime.format(string: String): String {
    return this.format(DateTimeFormatter.ofPattern(string))
}