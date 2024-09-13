package ru.topbun.cherry_tip.data.source.local.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

typealias Settings = DataStore<Preferences>

expect fun createDataStore(context: Any? = null): Settings

