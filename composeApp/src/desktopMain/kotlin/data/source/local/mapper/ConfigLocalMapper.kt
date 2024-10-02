package data.source.local.mapper

import data.source.local.entity.ThemeEntity
import domain.model.AppTheme
import domain.model.ThemesModel

class ConfigLocalMapper {

    fun mapToModel(entity: ThemeEntity) = AppTheme(
        name = entity.name,
        primaryColor = entity.primaryColor,
        secondaryColor = entity.secondaryColor,
        backgroundColor = entity.backgroundColor,
        surfaceColor = entity.surfaceColor,
        onPrimaryColor = entity.onPrimaryColor,
        onSecondaryColor = entity.onSecondaryColor,
        onBackgroundColor = entity.onBackgroundColor,
        onSurfaceColor = entity.onSurfaceColor
    )

    fun mapToThemesModel(currentTheme: String, themes: List<String>) = ThemesModel(
        currentTheme = currentTheme,
        allThemes = themes
    )

}