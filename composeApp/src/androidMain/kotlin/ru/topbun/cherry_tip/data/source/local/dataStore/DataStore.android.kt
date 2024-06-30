package ru.topbun.cherry_tip.data.source.local.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual fun createDataStore(context: Any?): DataStore<Preferences> {
    require(
        value = context is Context
    )
    return AppSettings.getDataStore{
        context.filesDir
            .resolve(AppSettings.FILE_NAME)
            .absolutePath
    }
}