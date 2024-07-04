package ru.topbun.cherry_tip.data.source.network.service

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import ru.topbun.cherry_tip.data.source.network.ApiFactory
import ru.topbun.cherry_tip.data.source.network.dto.auth.LoginDto
import ru.topbun.cherry_tip.data.source.network.dto.auth.SignUpDto

class AuthApi(
    private val api: ApiFactory
){

    suspend fun login(login: LoginDto) = api.client.post("/v1/auth/login"){
        setBody(login)
    }

    suspend fun signUp(signUp: SignUpDto) = api.client.post("/v1/auth/register"){
        setBody(signUp)
    }
}