package ru.topbun.cherry_tip.domain.repository

import ru.topbun.cherry_tip.domain.entity.LoginEntity
import ru.topbun.cherry_tip.domain.entity.SignUpEntity

interface AuthRepository {

    suspend fun login(login: LoginEntity)
    suspend fun singUp(signUp: SignUpEntity)

}