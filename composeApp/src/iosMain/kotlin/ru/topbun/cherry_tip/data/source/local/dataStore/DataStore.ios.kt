package ru.topbun.cherry_tip.data.source.local.dataStore

import ru.topbun.cherry_tip.data.source.local.dataStore.Settings
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
actual fun createDataStore(context: Any?): Settings {
    return AppSettings.getDataStore(
        producePath = {
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
            requireNotNull(documentDirectory).path + "/$dataStoreFileName"
        }
    )
}