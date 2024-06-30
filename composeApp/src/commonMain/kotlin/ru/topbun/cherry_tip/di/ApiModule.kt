package ru.topbun.cherry_tip.di

import com.arkivanov.mvikotlin.extensions.coroutines.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.topbun.cherry_tip.data.remote.ApiFactory
import ru.topbun.cherry_tip.data.remote.ApiFactoryImpl
import ru.topbun.cherry_tip.data.remote.service.AuthApiService

val apiModule = module {
    single<ApiFactory> { ApiFactoryImpl() }
    single { AuthApiService(get()) }
}