package ru.topbun.cherry_tip.data.source.network.dto

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class ErrorDto(
    @SerialName("message") val message: JsonElement,
    @SerialName("error") val error: String,
    @SerialName("statusCode") val statusCode: Int
)

object StringOrStringArraySerializer : KSerializer<List<String>> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("StringOrStringArray", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: List<String>) {
        if (value.size == 1) {
            encoder.encodeString(value[0])
        } else {
            encoder.encodeSerializableValue(
                JsonArray.serializer(),
                JsonArray(value.map { JsonPrimitive(it) })
            )
        }
    }

    override fun deserialize(decoder: Decoder): List<String> {
        return when (val jsonElement = (decoder as JsonDecoder).decodeJsonElement()) {
            is JsonPrimitive -> listOf(jsonElement.content)
            is JsonArray -> jsonElement.map { it.jsonPrimitive.content }
            else -> throw IllegalStateException("Unexpected JSON element")
        }
    }
}