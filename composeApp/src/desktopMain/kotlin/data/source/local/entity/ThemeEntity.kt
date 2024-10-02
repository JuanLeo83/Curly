package data.source.local.entity

import kotlinx.serialization.Serializable

@Serializable
data class ThemeEntity(
    val name: String,
    val primaryColor: String,
    val secondaryColor: String,
    val backgroundColor: String,
    val surfaceColor: String,
    val onPrimaryColor: String,
    val onSecondaryColor: String,
    val onBackgroundColor: String,
    val onSurfaceColor: String
)
