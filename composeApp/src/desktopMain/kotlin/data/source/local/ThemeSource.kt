package data.source.local

import data.source.local.entity.ThemeEntity
import data.source.local.mapper.ConfigLocalMapper
import domain.model.AppTheme
import kotlinx.serialization.json.Json
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import kotlin.io.path.extension
import kotlin.io.path.name
import kotlin.io.path.pathString

class ThemeSource(private val mapper: ConfigLocalMapper) {

    fun loadCurrentTheme(configDir: Path, theme: String): Result<AppTheme> {
        val themeFileName = theme + JSON_EXTENSION
        val themeFile = Paths.get(getThemeDirectory(configDir).pathString, themeFileName)
        val themeData = Files.readString(themeFile)
        val currentTheme = Json.decodeFromString<ThemeEntity>(themeData)
        return Result.success(mapper.mapToModel(currentTheme))
    }

    fun loadAllThemes(configDir: Path): List<String> {
        val themeDir = getThemeDirectory(configDir)
        return Files.list(themeDir)
            .filter { Files.isRegularFile(it) }
            .filter { it.extension == JSON }
            .map { it.fileName.name.substring(0, it.fileName.name.lastIndexOf(JSON_EXTENSION)) }
            .sorted()
            .toList()
    }

    fun importTheme(configDir: Path, path: String): Result<Unit> {
        val folder = path.substring(0, path.lastIndexOf(PATH_SEPARATOR))
        val fileName = path.substring(path.lastIndexOf(PATH_SEPARATOR) + 1)
        val source = Paths.get(folder, fileName)
        val destiny = getThemeDirectory(configDir).pathString + PATH_SEPARATOR + fileName
        copyFile(source.pathString, destiny)
        return Result.success(Unit)
    }

    private fun getThemeDirectory(configDir: Path): Path {
        val themeDir = Paths.get(configDir.toString(), THEMES_FOLDER)

        if (Files.notExists(themeDir)) {
            Files.createDirectories(themeDir)
            println("Theme directory created at: $themeDir")
        }

        return themeDir
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
    }

}