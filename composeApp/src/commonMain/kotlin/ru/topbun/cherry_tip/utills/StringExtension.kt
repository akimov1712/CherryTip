package ru.topbun.cherry_tip.utills

import androidx.compose.runtime.remember


fun String.validEmail() = Regex("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$").matches(this)

fun String.isNumber() = this.matches(Regex("^\\d+\$"))

fun Any?.toStringOrBlank() = this?.toString() ?: ""