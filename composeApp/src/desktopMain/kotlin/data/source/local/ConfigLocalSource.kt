package data.source.local

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.Locale
import kotlin.io.path.pathString

interface ConfigLocalSource {
    fun createConfigDirectory(): Result<Unit>
    fun getUserHome(): Result<String>
    fun loadAllThemes(): Result<List<String>>
    fun importTheme(path: String): Result<Unit>
}

class ConfigLocalSourceImpl : ConfigLocalSource {

    override fun createConfigDirectory(): Result<Unit> {
        val configDir = getConfigDirectory()
        return if (Files.notExists(configDir)) {
            Files.createDirectories(configDir)
            println("Configuration directory created at: $configDir")
            Result.success(Unit)
        } else {
            Result.failure(Exception("Configuration directory already exists at: $configDir"))
        }
    }

    override fun getUserHome(): Result<String> {
        return Result.success(System.getProperty(USER_HOME))
    }

    override fun loadAllThemes(): Result<List<String>> {
        val themeDir = getThemeDirectory()
        val themes = Files.list(themeDir)
            .filter { Files.isRegularFile(it) }
            .map { it.fileName.toString() }
            .sorted()
            .toList()
        return Result.success(themes)
    }

    override fun importTheme(path: String): Result<Unit> {
        val folder = path.substring(0, path.lastIndexOf(PATH_SEPARATOR))
        val fileName = path.substring(path.lastIndexOf(PATH_SEPARATOR) + 1)
        println(folder)
        println(fileName)
        val source = Paths.get(folder, fileName)
        val destiny = getThemeDirectory().pathString + PATH_SEPARATOR + fileName
        copyFile(source.pathString, destiny)
        return Result.success(Unit)
    }

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

    private fun getThemeDirectory(): Path {
        val configDir = getConfigDirectory()
        val themeDir = Paths.get(configDir.toString(), THEMES_FOLDER)
        return if (Files.notExists(themeDir)) {
            Files.createDirectories(themeDir)
            println("Theme directory created at: $themeDir")
            themeDir
        } else {
            themeDir
        }
    }

    private fun copyFile(sourcePath: String, destinationPath: String) {
        val source: Path = Paths.get(sourcePath)
        val destination: Path = Paths.get(destinationPath)

        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING)
        println("File copied from $sourcePath to $destinationPath")
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
        private const val THEMES_FOLDER = "themes"
        private const val PATH_SEPARATOR = "/"
    }
}