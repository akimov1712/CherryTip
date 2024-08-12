package ru.topbun.cherry_tip.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.topbun.cherry_tip.domain.useCases.auth.LoginUseCase
import ru.topbun.cherry_tip.domain.useCases.auth.SignUpUseCase
import ru.topbun.cherry_tip.domain.useCases.challenge.CancelChallengeUseCase
import ru.topbun.cherry_tip.domain.useCases.challenge.GetChallengeUseCase
import ru.topbun.cherry_tip.domain.useCases.challenge.GetUserChallengeByIdUseCase
import ru.topbun.cherry_tip.domain.useCases.challenge.StartChallengeUseCase
import ru.topbun.cherry_tip.domain.useCases.glass.AddDrinkGlassUseCase
import ru.topbun.cherry_tip.domain.useCases.glass.GetCountGlassUseCase
import ru.topbun.cherry_tip.domain.useCases.recipe.CreateRecipeUseCase
import ru.topbun.cherry_tip.domain.useCases.recipe.DeleteRecipeUseCase
import ru.topbun.cherry_tip.domain.useCases.recipe.EditRecipeUseCase
import ru.topbun.cherry_tip.domain.useCases.recipe.GetCategoriesUseCase
import ru.topbun.cherry_tip.domain.useCases.recipe.GetRecipeWithIdUseCase
import ru.topbun.cherry_tip.domain.useCases.recipe.GetRecipesUseCase
import ru.topbun.cherry_tip.domain.useCases.user.CheckAccountInfoCompleteUseCase
import ru.topbun.cherry_tip.domain.useCases.user.CreateGoalUseCase
import ru.topbun.cherry_tip.domain.useCases.user.CreateProfileUseCase
import ru.topbun.cherry_tip.domain.useCases.user.CreateUnitsUseCase
import ru.topbun.cherry_tip.domain.useCases.user.GetAccountInfoUseCase
import ru.topbun.cherry_tip.domain.useCases.user.LogOutUseCase
import ru.topbun.cherry_tip.domain.useCases.user.TokenIsValidUseCase
import ru.topbun.cherry_tip.domain.useCases.user.UpdateGoalUseCase
import ru.topbun.cherry_tip.domain.useCases.user.UpdateProfileUseCase
import ru.topbun.cherry_tip.domain.useCases.user.UpdateUnitsUseCase

val useCaseModule = module {
    authModule()
    userModule()
    glassModule()
    challengeModule()
    recipeModule()
}

private fun Module.recipeModule(){
    single { CreateRecipeUseCase(get()) }
    single { DeleteRecipeUseCase(get()) }
    single { EditRecipeUseCase(get()) }
    single { GetCategoriesUseCase(get()) }
    single { GetRecipesUseCase(get()) }
    single { GetRecipeWithIdUseCase(get()) }
}

private fun Module.challengeModule(){
    single { GetChallengeUseCase(get()) }
    single { StartChallengeUseCase(get()) }
    single { CancelChallengeUseCase(get()) }
    single { GetUserChallengeByIdUseCase(get()) }
}

private fun Module.glassModule(){
    single { GetCountGlassUseCase(get()) }
    single { AddDrinkGlassUseCase(get()) }
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
    single { LogOutUseCase(get()) }
}

private fun Module.authModule(){
    single { LoginUseCase(get()) }
    single { SignUpUseCase(get()) }
}