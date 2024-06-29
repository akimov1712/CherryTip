package ru.topbun.cherry_tip.utills


fun String.validEmail() = Regex("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$").matches(this)