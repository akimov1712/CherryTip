package ru.topbun.cherry_tip

import android.app.Application
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.topbun.cherry_tip.di.appModule
import ru.topbun.cherry_tip.utills.Const

class App: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            androidLogger()
            modules(appModule)
        }
        val config = AppMetricaConfig.newConfigBuilder(Const.APP_METRIC_API_KEY).build()
        AppMetrica.activate(this, config)
    }
}