package ru.topbun.cherry_tip.data.source.local.dataStore

import android.content.Context
import ru.topbun.cherry_tip.data.source.local.dataStore.Settings

actual fun createDataStore(context: Any?): Settings {
    require(
        value = context is Context
    )
    return AppSettings.getDataStore{
        context.filesDir
            .resolve(AppSettings.FILE_NAME)
            .absolutePath
    }
}