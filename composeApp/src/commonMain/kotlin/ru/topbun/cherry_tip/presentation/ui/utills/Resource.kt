package ru.topbun.cherry_tip.presentation.ui.utills

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cherrytip.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun getFileFromResource(path: String, key: Any? = Unit): MutableState<String> {
    var fileString = remember { mutableStateOf("") }
    LaunchedEffect(key) { fileString.value = Res.readBytes(path).decodeToString() }
    return fileString
}
