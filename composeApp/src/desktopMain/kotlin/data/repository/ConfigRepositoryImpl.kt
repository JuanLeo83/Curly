package data.repository

import data.source.local.ConfigLocalSource
import domain.model.ThemeModel
import domain.model.ThemesModel
import domain.repository.ConfigRepository

class ConfigRepositoryImpl(private val source: ConfigLocalSource) : ConfigRepository {

    override suspend fun createConfigDirectory(): Result<Unit> = source.createConfigDirectory()

    override fun getUserHome(): Result<String> = source.getUserHome()

    override suspend fun getAllThemes(): Result<ThemesModel> = source.loadAllThemes()

    override suspend fun importTheme(path: String): Result<Unit> = source.importTheme(path)

    override suspend fun setTheme(name: String): Result<ThemeModel> = source.setTheme(name)

    override suspend fun loadCurrentTheme(): Result<ThemeModel> = source.loadCurrentTheme()

}