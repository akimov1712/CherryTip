package ru.topbun.cherry_tip.data.source.network.service

import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import ru.topbun.cherry_tip.data.source.network.ApiFactory
import ru.topbun.cherry_tip.data.source.network.dto.user.GoalDto
import ru.topbun.cherry_tip.data.source.network.dto.user.ProfileDto
import ru.topbun.cherry_tip.data.source.network.dto.user.UnitsDto
import ru.topbun.cherry_tip.data.source.network.token

class UserApi(
    private val api: ApiFactory
) {

    suspend fun createProfile(profile: ProfileDto, token: String) = api.client.post("/v1/user/profile"){
        setBody(profile)
        token(token)
    }

    suspend fun updateProfile(profile: ProfileDto, token: String) = api.client.put("/v1/user/profile"){
        setBody(profile)
        token(token)
    }

    suspend fun createGoal(goal: GoalDto, token: String) = api.client.post("/v1/user/goal"){
        setBody(goal)
        token(token)
    }

    suspend fun updateGoal(goal: GoalDto, token: String) = api.client.put("/v1/user/goal"){
        setBody(goal)
        token(token)
    }

    suspend fun createUnits(units: UnitsDto, token: String) = api.client.post("/v1/user/units"){
        setBody(units)
        token(token)
    }

    suspend fun updateUnits(units: UnitsDto, token: String) = api.client.put("/v1/user/units"){
        setBody(units)
        token(token)
    }

    suspend fun getAccountInfo(token: String) = api.client.get("/v1/user/account"){
        token(token)
    }

}