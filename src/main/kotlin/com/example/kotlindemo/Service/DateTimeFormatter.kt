package com.example.kotlindemo.Service

import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class DateTimeFormatter(val format: String){

    val current = LocalDateTime.now()

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
    val formatted = current.format(formatter)

}