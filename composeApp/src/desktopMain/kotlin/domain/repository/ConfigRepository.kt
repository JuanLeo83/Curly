package domain.repository

import domain.model.ThemeModel
import domain.model.ThemesModel


interface ConfigRepository {
    suspend fun createConfigDirectory(): Result<Unit>
    fun getUserHome(): Result<String>
    suspend fun getAllThemes(): Result<ThemesModel>
    suspend fun importTheme(path: String): Result<Unit>
    suspend fun setTheme(name: String): Result<ThemeModel>
    suspend fun loadCurrentTheme(): Result<ThemeModel>
}