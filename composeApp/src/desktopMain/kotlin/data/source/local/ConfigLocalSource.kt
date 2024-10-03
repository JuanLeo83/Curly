package data.source.local

import curly.composeapp.generated.resources.Res
import data.source.local.entity.ConfigEntity
import data.source.local.mapper.ConfigLocalMapper
import domain.error.ConfigFileAlreadyExistsException
import domain.error.CreateConfigException
import domain.model.AppTheme
import domain.model.ThemesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Locale
import kotlin.io.path.pathString

interface ConfigLocalSource {
    suspend fun createConfigDirectory(): Result<Unit>
    fun getUserHome(): Result<String>
    fun loadAllThemes(): Result<ThemesModel>
    fun importTheme(path: String): Result<Unit>
    fun setTheme(name: String): Result<AppTheme>
    fun loadCurrentTheme(): Result<AppTheme>
}

@OptIn(ExperimentalResourceApi::class)
class ConfigLocalSourceImpl(
    private val themeSource: ThemeSource,
    private val mapper: ConfigLocalMapper
) : ConfigLocalSource {

    override suspend fun createConfigDirectory(): Result<Unit> {
        val configDir = getConfigDirectory()
        return if (Files.notExists(configDir)) {
            withContext(Dispatchers.IO) {
                Files.createDirectories(configDir)
                println("Configuration directory created at: $configDir")
                setConfigFile(configDir)
            }
        } else createConfigFileIfNotExists(configDir)
    }

    override fun getUserHome(): Result<String> =
        Result.success(System.getProperty(USER_HOME))

    override fun loadAllThemes(): Result<ThemesModel> {
        val currentTheme = readConfigFile().theme
        val allThemes = themeSource.loadAllThemes(getConfigDirectory())
        return Result.success(mapper.mapToThemesModel(currentTheme, allThemes))
    }

    override fun importTheme(path: String): Result<Unit> =
        themeSource.importTheme(getConfigDirectory(), path)

    override fun setTheme(name: String): Result<AppTheme> {
        val config = readConfigFile().copy(theme = name)
        // TODO: write config file
        return themeSource.loadCurrentTheme(getConfigDirectory(), config)
    }

    override fun loadCurrentTheme(): Result<AppTheme> =
        themeSource.loadCurrentTheme(getConfigDirectory(), readConfigFile())

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
        val configContent = Res.readBytes(CONFIG_PATH).decodeToString()
        val destiny = configDirectory.pathString + PATH_SEPARATOR + CONFIG_FILE
        val configFile = File(destiny)
        return try {
            withContext(Dispatchers.IO) {
                FileWriter(configFile).use { writer ->
                    writer.write(configContent)
                }
            }
            println("File written successfully at: $destiny")
            Result.success(Unit)
        } catch (e: IOException) {
            e.printStackTrace()
            Result.failure(CreateConfigException())
        }
    }

    private fun readConfigFile(): ConfigEntity {
        val configFile = Paths.get(getConfigDirectory().toString(), CONFIG_FILE)
        val textContent = Files.readString(configFile)
        return Json.decodeFromString<ConfigEntity>(textContent)
    }

    private suspend fun createConfigFileIfNotExists(configDir: Path): Result<Unit> {
        val configFile = Paths.get(configDir.toString(), CONFIG_FILE)
        return if (Files.notExists(configFile)) {
            setConfigFile(configDir)
        } else Result.failure(ConfigFileAlreadyExistsException())
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
        private const val CONFIG_PATH = "files/$CONFIG_FILE"
    }
}