package ru.topbun.cherry_tip.data.source.local.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import okio.Path.Companion.toPath

object AppSettings {

    const val FILE_NAME = "settings.preferences_pb"
    private val lock = SynchronizedObject()
    private lateinit var dataStore: DataStore<Preferences>

    fun getDataStore(producePath: () -> String): DataStore<Preferences>{
        return synchronized(lock){
            if (AppSettings::dataStore.isInitialized) dataStore
            else PreferenceDataStoreFactory.createWithPath {
                producePath().toPath()
            }.also { dataStore = it }
        }
    }


    val KEY_TOKEN = stringPreferencesKey(name = "token")
    val KEY_GLASS = stringPreferencesKey(name = "count_glass")

}