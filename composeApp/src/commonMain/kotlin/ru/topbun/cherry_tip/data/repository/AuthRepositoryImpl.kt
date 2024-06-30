package ru.topbun.cherry_tip.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import io.ktor.client.call.body
import ru.topbun.cherry_tip.data.mapper.toDto
import ru.topbun.cherry_tip.data.source.local.dataStore.AppSettings
import ru.topbun.cherry_tip.data.source.network.service.AuthApiService
import ru.topbun.cherry_tip.domain.entity.LoginEntity
import ru.topbun.cherry_tip.domain.entity.SignUpEntity
import ru.topbun.cherry_tip.domain.repository.AuthRepository
import ru.topbun.cherry_tip.utills.ParseBackendResponseException
import ru.topbun.cherry_tip.utills.exceptionWrapper


class AuthRepositoryImpl(
    private val authApi: AuthApiService,
    private val dataStore: DataStore<Preferences>
): AuthRepository {

    override suspend fun login(login: LoginEntity){
        val response = authApi.login(login.toDto()).exceptionWrapper()
        try {
            dataStore.edit {
                it[AppSettings.KEY_TOKEN] = response.body<String>()
            }
        } catch (e: Exception){
            throw ParseBackendResponseException()
        }
    }


    override suspend fun singUp(signUp: SignUpEntity){
        authApi.signUp(signUp.toDto()).exceptionWrapper()
    }
}