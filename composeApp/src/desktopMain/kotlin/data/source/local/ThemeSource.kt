package data.source.local

import curly.composeapp.generated.resources.Res
import data.source.local.entity.ThemeEntity
import data.source.local.mapper.ConfigLocalMapper
import domain.model.ThemeModel
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
import java.nio.file.StandardCopyOption
import kotlin.io.path.extension
import kotlin.io.path.name
import kotlin.io.path.pathString

@OptIn(ExperimentalResourceApi::class)
class ThemeSource(private val mapper: ConfigLocalMapper) {

    suspend fun getThemeDirectory(configDir: Path): Path {
        val themeDir = Paths.get(configDir.toString(), THEMES_FOLDER)

        if (Files.notExists(themeDir)) {
            withContext(Dispatchers.IO) { Files.createDirectories(themeDir) }
            println("Theme directory created at: $themeDir")
            createDefaultThemes(themeDir)
        }

        return themeDir
    }

    suspend fun loadCurrentTheme(configDir: Path, theme: String): Result<ThemeModel> {
        val themeFileName = theme + JSON_EXTENSION
        val themeFile = Paths.get(getThemeDirectory(configDir).pathString, themeFileName)
        val themeData = withContext(Dispatchers.IO) { Files.readString(themeFile) }
        val currentTheme = Json.decodeFromString<ThemeEntity>(themeData)
        return Result.success(mapper.mapToModel(currentTheme))
    }

    suspend fun loadAllThemes(configDir: Path): List<String> {
        val themeDir = getThemeDirectory(configDir)
        return withContext(Dispatchers.IO) {
            Files.list(themeDir)
                .filter { Files.isRegularFile(it) }
                .filter { it.extension == JSON }
                .map { it.fileName.name.substring(0, it.fileName.name.lastIndexOf(JSON_EXTENSION)) }
                .sorted()
                .toList()
        }
    }

    suspend fun importTheme(configDir: Path, path: String): Result<Unit> {
        val folder = path.substring(0, path.lastIndexOf(PATH_SEPARATOR))
        val fileName = path.substring(path.lastIndexOf(PATH_SEPARATOR) + 1)
        val source = Paths.get(folder, fileName)
        val destiny = getThemeDirectory(configDir).pathString + PATH_SEPARATOR + fileName
        copyFile(source.pathString, destiny)
        return Result.success(Unit)
    }

    private suspend fun createDefaultThemes(themeDir: Path) {
        val defaultThemes = listOf(DEFAULT_LIGHT_THEME, DEFAULT_DARK_THEME)
        defaultThemes.forEach { theme ->
            val themeContent = Res.readBytes("$THEMES_PATH$PATH_SEPARATOR$theme").decodeToString()
            val destiny = themeDir.pathString + PATH_SEPARATOR + theme
            val themeFile = File(destiny)
            try {
                withContext(Dispatchers.IO) {
                    FileWriter(themeFile).use { writer ->
                        writer.write(themeContent)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun copyFile(sourcePath: String, destinationPath: String) {
        val source: Path = Paths.get(sourcePath)
        val destination: Path = Paths.get(destinationPath)

        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING)
        println("File copied from $sourcePath to $destinationPath")
    }

    companion object {
        private const val THEMES_FOLDER = "themes"
        private const val PATH_SEPARATOR = "/"
        private const val JSON_EXTENSION = ".json"
        private const val JSON = "json"
        private const val DEFAULT_LIGHT_THEME = "CurlyLight$JSON_EXTENSION"
        private const val DEFAULT_DARK_THEME = "CurlyDark$JSON_EXTENSION"
        private const val THEMES_PATH = "files/themes"
    }

}