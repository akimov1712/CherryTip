package ru.topbun.cherry_tip.utills

import androidx.compose.runtime.remember


fun String.validEmail() = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$").matches(this)

fun String.isNumber() = this.matches(Regex("^\\d+\$"))

fun Any?.toStringOrBlank() = this?.toString() ?: ""
fun String.resolveDomain() = Const.BASE_URL + this.drop(1)