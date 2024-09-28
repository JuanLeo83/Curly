package data.repository

import data.source.local.ConfigLocalSource
import domain.repository.ConfigRepository

class ConfigRepositoryImpl(private val source: ConfigLocalSource) : ConfigRepository {

    override fun createConfigDirectory(): Result<Unit> {
        return source.createConfigDirectory()
    }

    override fun getUserHome(): Result<String> {
        return source.getUserHome()
    }

    override fun getAllThemes(): Result<List<String>> {
        return Result.success(emptyList())
    }

    override fun importTheme(path: String): Result<Unit> {
        return source.importTheme(path)
    }

    override fun loadTheme(name: String) {
        // Load theme from config file
    }
}