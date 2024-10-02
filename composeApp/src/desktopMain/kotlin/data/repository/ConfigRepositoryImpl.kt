package data.repository

import data.source.local.ConfigLocalSource
import domain.model.ThemesModel
import domain.repository.ConfigRepository

class ConfigRepositoryImpl(private val source: ConfigLocalSource) : ConfigRepository {

    override suspend fun createConfigDirectory(): Result<Unit> {
        return source.createConfigDirectory()
    }

    override fun getUserHome(): Result<String> {
        return source.getUserHome()
    }

    override fun getAllThemes(): Result<ThemesModel> {
        return source.loadAllThemes()
    }

    override fun importTheme(path: String): Result<Unit> {
        return source.importTheme(path)
    }

    override fun setTheme(name: String): Result<Unit> {
        // TODO: Implement
        return Result.success(Unit)
    }

    override fun loadCurrentTheme(): Result<Unit> {
        return Result.success(Unit)
    }

}