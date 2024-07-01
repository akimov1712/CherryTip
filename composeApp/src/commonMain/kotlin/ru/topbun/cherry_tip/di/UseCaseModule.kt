package ru.topbun.cherry_tip.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.topbun.cherry_tip.domain.useCases.auth.LoginUseCase
import ru.topbun.cherry_tip.domain.useCases.auth.SignUpUseCase

val useCaseModule = module {
    single { LoginUseCase(get()) }
    single { SignUpUseCase(get()) }
}