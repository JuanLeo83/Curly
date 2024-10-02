package domain.repository

import domain.model.ThemesModel


interface ConfigRepository {
    suspend fun createConfigDirectory(): Result<Unit>
    fun getUserHome(): Result<String>
    fun getAllThemes(): Result<ThemesModel>
    fun importTheme(path: String): Result<Unit>
    fun setTheme(name: String): Result<Unit>
    fun loadCurrentTheme(): Result<Unit>
}