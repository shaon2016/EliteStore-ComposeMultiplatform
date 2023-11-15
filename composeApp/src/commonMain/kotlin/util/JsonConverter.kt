package util

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object JsonConverter {
    inline fun <reified T> fromJson(json: String): T = Json.decodeFromString(json)
    inline fun <reified T> toJson(value: T) = Json.encodeToString(value)
}