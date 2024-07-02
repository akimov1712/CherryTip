package ru.topbun.cherry_tip.di

import org.koin.dsl.module

val appModule = module {
    includes(
        apiModule,
        repositoryModule,
        localModule,
        useCaseModule,
        storeModule,
        componentModule,
    )
}