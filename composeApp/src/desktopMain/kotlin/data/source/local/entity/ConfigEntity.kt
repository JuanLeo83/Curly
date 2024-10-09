package data.source.local.entity

import kotlinx.serialization.Serializable

@Serializable
data class ConfigEntity(
    val theme: String = "defaultTheme",
    val language: String = "en"
)
