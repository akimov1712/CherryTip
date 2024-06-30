package ru.topbun.cherry_tip.data.remote.service

import io.ktor.client.request.get
import io.ktor.http.headers
import ru.topbun.cherry_tip.data.remote.ApiFactory
import ru.topbun.cherry_tip.data.remote.ApiFactoryImpl
import ru.topbun.cherry_tip.data.remote.dto.LoginDto
import ru.topbun.cherry_tip.data.remote.dto.SignUpDto

class AuthApiService(
    private val api: ApiFactory
): ApiFactory by api {

    suspend fun login(login: LoginDto) = api.client.get {
        apiUrl("v1/auth/login")
    }

    suspend fun signUp(signUp: SignUpDto) = api.client.get {
        apiUrl("/v1/auth/register")
        headers {
            append("Authorization", "application/json")
        }
    }
}