package ru.topbun.cherry_tip.domain.repository

import ru.topbun.cherry_tip.domain.entity.auth.LoginEntity
import ru.topbun.cherry_tip.domain.entity.auth.SignUpEntity

interface AuthRepository {

    suspend fun login(login: LoginEntity)
    suspend fun singUp(signUp: SignUpEntity)

}