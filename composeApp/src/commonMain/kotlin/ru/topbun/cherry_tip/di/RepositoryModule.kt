package ru.topbun.cherry_tip.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.topbun.cherry_tip.data.repository.AuthRepositoryImpl
import ru.topbun.cherry_tip.domain.repository.AuthRepository

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}
