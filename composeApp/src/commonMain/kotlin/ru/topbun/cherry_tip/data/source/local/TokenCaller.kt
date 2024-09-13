package ru.topbun.cherry_tip.data.source.local

import ru.topbun.cherry_tip.data.source.local.dataStore.Settings
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import ru.topbun.cherry_tip.data.source.local.dataStore.AppSettings
import ru.topbun.cherry_tip.utills.FailedExtractTokenException

suspend fun Settings.getToken(): String {
    val token = this.data
        .map { it[AppSettings.KEY_TOKEN] }
        .firstOrNull()
    return token ?: throw FailedExtractTokenException()
}
