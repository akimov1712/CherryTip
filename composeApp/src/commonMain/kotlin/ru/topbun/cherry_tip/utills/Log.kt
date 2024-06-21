package ru.topbun.cherry_tip.utills

import io.ktor.util.date.GMTDate
import io.ktor.util.date.getTimeMillis

object Log {

    fun d(tag: String?, message: String) = println("D/$tag: $message")

}