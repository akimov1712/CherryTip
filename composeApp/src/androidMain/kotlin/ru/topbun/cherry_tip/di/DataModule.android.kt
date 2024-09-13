package ru.topbun.cherry_tip.di

import ru.topbun.cherry_tip.data.source.local.dataStore.Settings
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.topbun.cherry_tip.data.source.local.dataStore.createDataStore

actual val localModule = module{
    single<Settings> {
        createDataStore(androidContext())
    }
}