package ru.topbun.cherry_tip.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.topbun.cherry_tip.domain.useCases.auth.LoginUseCase
import ru.topbun.cherry_tip.domain.useCases.auth.SignUpUseCase
import ru.topbun.cherry_tip.domain.useCases.user.CheckAccountInfoCompleteUseCase
import ru.topbun.cherry_tip.domain.useCases.user.CreateGoalUseCase
import ru.topbun.cherry_tip.domain.useCases.user.CreateProfileUseCase
import ru.topbun.cherry_tip.domain.useCases.user.CreateUnitsUseCase
import ru.topbun.cherry_tip.domain.useCases.user.GetAccountInfoUseCase
import ru.topbun.cherry_tip.domain.useCases.user.TokenIsValidUseCase
import ru.topbun.cherry_tip.domain.useCases.user.UpdateGoalUseCase
import ru.topbun.cherry_tip.domain.useCases.user.UpdateProfileUseCase
import ru.topbun.cherry_tip.domain.useCases.user.UpdateUnitsUseCase

val useCaseModule = module {
    authModule()
    userModule()
}

private fun Module.userModule(){
    single { CreateProfileUseCase(get()) }
    single { UpdateProfileUseCase(get()) }
    single { CreateGoalUseCase(get()) }
    single { UpdateGoalUseCase(get()) }
    single { CreateUnitsUseCase(get()) }
    single { UpdateUnitsUseCase(get()) }
    single { GetAccountInfoUseCase(get()) }
    single { TokenIsValidUseCase(get()) }
    single { CheckAccountInfoCompleteUseCase(get()) }
}

private fun Module.authModule(){
    single { LoginUseCase(get()) }
    single { SignUpUseCase(get()) }
}