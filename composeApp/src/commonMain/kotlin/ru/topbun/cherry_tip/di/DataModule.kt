package ru.topbun.cherry_tip.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.topbun.cherry_tip.data.repository.AuthRepositoryImpl
import ru.topbun.cherry_tip.data.repository.ChallengeRepositoryImpl
import ru.topbun.cherry_tip.data.repository.GlassRepositoryImpl
import ru.topbun.cherry_tip.data.repository.RecipeRepositoryImpl
import ru.topbun.cherry_tip.data.repository.UserRepositoryImpl
import ru.topbun.cherry_tip.data.source.network.ApiFactory
import ru.topbun.cherry_tip.data.source.network.service.AuthApi
import ru.topbun.cherry_tip.data.source.network.service.ChallengeApi
import ru.topbun.cherry_tip.data.source.network.service.RecipeApi
import ru.topbun.cherry_tip.data.source.network.service.UserApi
import ru.topbun.cherry_tip.domain.repository.AuthRepository
import ru.topbun.cherry_tip.domain.repository.ChallengeRepository
import ru.topbun.cherry_tip.domain.repository.GlassRepository
import ru.topbun.cherry_tip.domain.repository.RecipeRepository
import ru.topbun.cherry_tip.domain.repository.UserRepository

val apiModule = module {
    single<ApiFactory> { ApiFactory() }
    single { AuthApi(get()) }
    single { UserApi(get()) }
    single { ChallengeApi(get()) }
    single { RecipeApi(get()) }
}

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<GlassRepository> { GlassRepositoryImpl(get(), get()) }
    single<ChallengeRepository> { ChallengeRepositoryImpl(get(), get()) }
    single<RecipeRepository> { RecipeRepositoryImpl(get(), get()) }
}

expect val localModule: Module