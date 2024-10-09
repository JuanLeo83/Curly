package domain.repository

import domain.model.AppTheme
import domain.model.ThemesModel


interface ConfigRepository {
    suspend fun createConfigDirectory(): Result<Unit>
    fun getUserHome(): Result<String>
    suspend fun getAllThemes(): Result<ThemesModel>
    fun importTheme(path: String): Result<Unit>
    suspend fun setTheme(name: String): Result<AppTheme>
    fun loadCurrentTheme(): Result<AppTheme>
}