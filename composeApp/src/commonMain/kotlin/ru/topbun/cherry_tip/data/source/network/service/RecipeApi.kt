package ru.topbun.cherry_tip.data.source.network.service

import io.ktor.client.request.delete
import io.ktor.client.request.forms.InputProvider
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.utils.io.core.buildPacket
import io.ktor.utils.io.core.writeFully
import ru.topbun.cherry_tip.data.mapper.toAddRecipeDto
import ru.topbun.cherry_tip.data.source.network.ApiFactory
import ru.topbun.cherry_tip.data.source.network.dto.recipe.RecipeDto
import ru.topbun.cherry_tip.data.source.network.token

class RecipeApi(
    private val api: ApiFactory
) {

    suspend fun uploadImage(image: ByteArray, token: String) = api.client.submitFormWithBinaryData(
        url = "/v1/recipe/upload/image",
        formData = formData {
            append("file", InputProvider {
                buildPacket {
                    writeFully(image)
                }
            }, Headers.build {
                append(HttpHeaders.ContentType, "image/jpeg")
                append(HttpHeaders.ContentDisposition, "filename=\"photo.jpg\"")
            })
        }
    ) { token(token) }


    suspend fun deleteRecipe(id: Int, token: String) = api.client.delete("/v1/recipe/$id") {
        token(token)
    }

    suspend fun editRecipe(recipe: RecipeDto, token: String) =
        api.client.put("/v1/recipe/${recipe.id}") {
            setBody(recipe)
            token(token)
        }

    suspend fun getRecipeWithId(id: Int) = api.client.get("/v1/recipe/$id")

    suspend fun createRecipe(recipe: RecipeDto, token: String) = api.client.post("/v1/recipe/") {
        setBody(recipe.toAddRecipeDto())
        token(token)
    }

    suspend fun getCategories() = api.client.get("/v1/recipe/tags")

    suspend fun getRecipes(
        q: String?,
        isMyRecipe: Boolean,
        take: Int?,
        skip: Int?,
        meal: Int?,
        diet: Int?,
        preparation: Int?,
        token: String
    ): HttpResponse {
        val route = "/v1/recipe/" + if (isMyRecipe) "my" else "search"
        return api.client.get(route) {
            parameter(QUERY, q)
            parameter(TAKE, take)
            parameter(SKIP, skip)
            parameter(CATEGORY, meal)
            parameter(DIET, diet)
            parameter(PREPARATION, preparation)
            if (isMyRecipe) token(token)
        }
    }

    private companion object {
        const val QUERY = "q"
        const val TAKE = "take"
        const val SKIP = "skip"
        const val CATEGORY = "category"
        const val DIET = "diet"
        const val PREPARATION = "preparation"
    }

}