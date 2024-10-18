package data.source.local

import data.source.local.entity.ConfigEntity
import data.source.local.mapper.ConfigLocalMapper
import domain.error.ReadConfigException
import domain.model.ThemeModel
import domain.model.ThemesModel
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.extensions.storeOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.Path.Companion.toPath
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Locale
import kotlin.io.path.pathString

interface ConfigLocalSource {
    suspend fun createConfigDirectory(): Result<Unit>
    fun getUserHome(): Result<String>
    suspend fun loadAllThemes(): Result<ThemesModel>
    suspend fun importTheme(path: String): Result<Unit>
    suspend fun setTheme(name: String): Result<ThemeModel>
    suspend fun loadCurrentTheme(): Result<ThemeModel>
}

class ConfigLocalSourceImpl(
    private val themeSource: ThemeSource,
    private val mapper: ConfigLocalMapper
) : ConfigLocalSource {

    private lateinit var store: KStore<ConfigEntity>

    override suspend fun createConfigDirectory(): Result<Unit> {
        val configDir = getConfigDirectory()
        return if (Files.notExists(configDir)) {
            withContext(Dispatchers.IO) {
                Files.createDirectories(configDir)
                println("Configuration directory created at: $configDir")
                themeSource.getThemeDirectory(configDir)
                setConfigFile(configDir)
            }
        } else setConfigFile(configDir)
    }

    override fun getUserHome(): Result<String> = Result.success(System.getProperty(USER_HOME))

    override suspend fun loadAllThemes(): Result<ThemesModel> {
        return store.get()?.let {
            val allThemes = themeSource.loadAllThemes(getConfigDirectory())
            Result.success(mapper.mapToThemesModel(it.theme, allThemes))
        } ?: Result.failure(ReadConfigException())
    }

    override suspend fun importTheme(path: String): Result<Unit> =
        themeSource.importTheme(getConfigDirectory(), path)

    override suspend fun setTheme(name: String): Result<ThemeModel> {
        store.update { it?.copy(theme = name) }
        return loadCurrentTheme()
    }

    override suspend fun loadCurrentTheme(): Result<ThemeModel> = store.get()?.let {
        themeSource.loadCurrentTheme(getConfigDirectory(), it.theme)
    } ?: Result.failure(ReadConfigException())

    private fun getConfigDirectory(): Path {
        val os = System.getProperty(PROPERTY_OS_NAME).lowercase(Locale.getDefault())
        val userHome = System.getProperty(USER_HOME)

        return when {
            os.contains(WINDOWS) ->
                Paths.get(userHome, WINDOWS_APP_DATA, WINDOWS_ROAMING, APP_CONFIG_FOLDER)

            os.contains(MAC) ->
                Paths.get(userHome, MAC_LIBRARY, MAC_APPLICATION_SUPPORT, APP_CONFIG_FOLDER)

            os.contains(UNIX) || os.contains(LINUX) || os.contains(OTHER) ->
                Paths.get(userHome, UNIX_CONFIG, APP_CONFIG_FOLDER)

            else -> throw UnsupportedOperationException("Unsupported operating system: $os")
        }
    }

    private suspend fun setConfigFile(configDirectory: Path): Result<Unit> {
        val destiny = configDirectory.pathString + PATH_SEPARATOR + CONFIG_FILE
        store = storeOf(file = destiny.toPath(), version = 1)
        store.set(ConfigEntity())
        return Result.success(Unit)
    }

    companion object {
        private const val APP_CONFIG_FOLDER = ".curly"
        private const val PROPERTY_OS_NAME = "os.name"
        private const val USER_HOME = "user.home"
        private const val WINDOWS = "win"
        private const val MAC = "mac"
        private const val UNIX = "nix"
        private const val LINUX = "nux"
        private const val OTHER = "aix"
        private const val WINDOWS_APP_DATA = "AppData"
        private const val WINDOWS_ROAMING = "Roaming"
        private const val MAC_LIBRARY = "Library"
        private const val MAC_APPLICATION_SUPPORT = "Application Support"
        private const val UNIX_CONFIG = ".config"
        private const val PATH_SEPARATOR = "/"
        private const val CONFIG_FILE = "config.json"
    }
}