package ru.topbun.cherry_tip

import org.koin.core.context.startKoin
import ru.topbun.cherry_tip.di.appModule

fun initKoin(){
    startKoin {
        modules(appModule)
    }
}