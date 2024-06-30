package ru.topbun.cherry_tip

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.topbun.cherry_tip.di.appModule

class App: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            androidLogger()
            modules(appModule)
        }
    }
}