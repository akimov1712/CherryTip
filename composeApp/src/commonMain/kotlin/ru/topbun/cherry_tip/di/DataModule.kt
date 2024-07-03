package ru.topbun.cherry_tip.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.topbun.cherry_tip.data.repository.AuthRepositoryImpl
import ru.topbun.cherry_tip.data.source.network.ApiFactory
import ru.topbun.cherry_tip.data.source.network.service.AuthApiService
import ru.topbun.cherry_tip.domain.repository.AuthRepository

val apiModule = module {
    single<ApiFactory> { ApiFactory() }
    single { AuthApiService(get()) }
}

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}

expect val localModule: Module