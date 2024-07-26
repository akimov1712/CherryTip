package ru.topbun.cherry_tip.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import ru.topbun.cherry_tip.data.source.local.dataStore.AppSettings
import ru.topbun.cherry_tip.utills.FailedExtractTokenException

suspend fun DataStore<Preferences>.getToken(): String {
    val token = this.data
        .map { it[AppSettings.KEY_TOKEN] }
        .firstOrNull()
    return token ?: throw FailedExtractTokenException()
}
