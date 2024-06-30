package ru.topbun.cherry_tip.data.repository

import io.ktor.client.call.body
import io.ktor.http.isSuccess
import ru.topbun.cherry_tip.data.mapper.toDto
import ru.topbun.cherry_tip.data.remote.dto.LoginDto
import ru.topbun.cherry_tip.data.remote.service.AuthApiService
import ru.topbun.cherry_tip.domain.entity.LoginEntity
import ru.topbun.cherry_tip.domain.entity.SignUpEntity
import ru.topbun.cherry_tip.domain.repository.AuthRepository
import ru.topbun.cherry_tip.utills.Log


class AuthRepositoryImpl(
    private val authApi: AuthApiService
): AuthRepository {

    override suspend fun login(login: LoginEntity) {
        val response = authApi.login(login.toDto())
        if (response.status.isSuccess()){
            val login = response.body<String>()
            Log.d("RESPONSE", login)
        } else {
            Log.d("RESPONSE", response.status.description)
        }
    }

    override suspend fun singUp(signUp: SignUpEntity) {
        // Реализация метода signUp
    }
}