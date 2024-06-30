package ru.topbun.cherry_tip.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.topbun.cherry_tip.data.source.local.dataStore.createDataStore

actual val localModule = module{
    single<DataStore<Preferences>> {
        createDataStore(androidContext())
    }
}