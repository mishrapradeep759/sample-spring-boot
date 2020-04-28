package com.example.kotlindemo.Utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

fun main(args: Array<String>) {

    val current = LocalDateTime.now()

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
    val formatted = current.format(formatter)

    val formatterISO = DateTimeFormatter.BASIC_ISO_DATE
    val formattedISO = current.format(formatter)

    val formatterLocalized = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
    val formattedLocalized = current.format(formatter)

    println("Current Date and Time is: $formatted")
}

interface RawDateTimeFormatter{
    val rawDate: String
    fun dateFormatter(date: String): String
}

open class GenericDateTimeFormatter : RawDateTimeFormatter{
    open val dateTime = LocalDateTime.now().toLocalDate()
    override val rawDate: String
        get() = "BasicFormatProvider"

    override fun dateFormatter(date: String): String {
        return rawDate
    }

}
