package data.source.local.entity

import kotlinx.serialization.Serializable

@Serializable
data class ConfigEntity(
    val theme: String,
    val language: String
)
