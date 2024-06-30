package ru.topbun.cherry_tip.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.ktor.client.call.DoubleReceiveException
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.TimeoutCancellationException
import ru.topbun.cherry_tip.data.mapper.toDto
import ru.topbun.cherry_tip.data.source.network.service.AuthApiService
import ru.topbun.cherry_tip.domain.ClientException
import ru.topbun.cherry_tip.domain.ParseBackendResponseException
import ru.topbun.cherry_tip.domain.RequestTimeoutException
import ru.topbun.cherry_tip.domain.ServerException
import ru.topbun.cherry_tip.domain.entity.LoginEntity
import ru.topbun.cherry_tip.domain.entity.SignUpEntity
import ru.topbun.cherry_tip.domain.repository.AuthRepository
import ru.topbun.cherry_tip.utills.Log
import ru.topbun.cherry_tip.utills.exceptionWrapper


class AuthRepositoryImpl(
    private val authApi: AuthApiService,
    private val dataStore: DataStore<Preferences>
): AuthRepository {

    override suspend fun login(login: LoginEntity) {
        val response = authApi.login(login.toDto()).exceptionWrapper()
    }


    override suspend fun singUp(signUp: SignUpEntity) {
        // Реализация метода signUp
    }
}