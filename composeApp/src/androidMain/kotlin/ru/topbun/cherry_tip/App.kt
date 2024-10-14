package ru.topbun.cherry_tip

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.topbun.cherry_tip.di.appModule
import ru.topbun.cherry_tip.presentation.NotifyWaterReceiver
import ru.topbun.cherry_tip.utills.Const
import java.util.Calendar


class App: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            androidLogger()
            modules(appModule)
        }
        setDailyAlarm()
        val config = AppMetricaConfig.newConfigBuilder(Const.APP_METRIC_API_KEY).build()
        AppMetrica.activate(this, config)
    }

    private fun setDailyAlarm(){
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = NotifyWaterReceiver.newIntent(this)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 12)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }


}