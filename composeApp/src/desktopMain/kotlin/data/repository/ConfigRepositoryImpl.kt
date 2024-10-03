package data.repository

import data.source.local.ConfigLocalSource
import domain.model.AppTheme
import domain.model.ThemesModel
import domain.repository.ConfigRepository

class ConfigRepositoryImpl(private val source: ConfigLocalSource) : ConfigRepository {

    override suspend fun createConfigDirectory(): Result<Unit> = source.createConfigDirectory()

    override fun getUserHome(): Result<String> = source.getUserHome()

    override fun getAllThemes(): Result<ThemesModel> = source.loadAllThemes()

    override fun importTheme(path: String): Result<Unit> = source.importTheme(path)

    override fun setTheme(name: String): Result<AppTheme> = source.setTheme(name)

    override fun loadCurrentTheme(): Result<AppTheme> {
        TODO("Pending implementation")
    }

}