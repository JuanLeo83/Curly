package domain.repository


interface ConfigRepository {
    suspend fun createConfigDirectory(): Result<Unit>
    fun getUserHome(): Result<String>
    fun getAllThemes(): Result<List<String>>
    fun importTheme(path: String): Result<Unit>
    fun loadTheme(name: String)
}