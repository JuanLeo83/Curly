package presentation.screen.settings

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.usecase.ApplyThemeUseCase
import domain.usecase.GetThemesUseCase
import domain.usecase.GetUserHomeUseCase
import domain.usecase.ImportThemeUseCase
import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.coroutines.launch
import presentation.theme.ThemeMapper
import theme

class SettingsScreenModel(
    private val getUserHomeUseCase: GetUserHomeUseCase,
    private val importThemeUseCase: ImportThemeUseCase,
    private val getThemesUseCase: GetThemesUseCase,
    private val applyThemeUseCase: ApplyThemeUseCase,
    private val themeMapper: ThemeMapper
) : StateScreenModel<SettingsScreenState>(SettingsScreenState()) {

    init {
        screenModelScope.launch {
            getUserHomeUseCase().fold(
                onSuccess = ::onGetUserHomeSuccess,
                onFailure = { println("Error getting user home directory: $it") }
            )
        }
    }

    fun importTheme(platformFile: PlatformFile?) {
        if (platformFile == null) return

        screenModelScope.launch {
            importThemeUseCase(platformFile.file.path).fold(
                onSuccess = { loadThemes() },
                onFailure = { println("Error importing theme: $it") }
            )
        }
    }

    fun applyTheme(themeName: String) {
        screenModelScope.launch {
            applyThemeUseCase(themeName).fold(
                onSuccess = {
                    theme = themeMapper.mapToTheme(it)
                    mutableState.value = state.value.copy(
                        currentTheme = themeName,
                        newThemeLoaded = true
                    )
                },
                onFailure = { println("Error applying theme: $it") }
            )
        }
    }

    fun resetNewThemeLoaded() {
        mutableState.value = state.value.copy(newThemeLoaded = false)
    }

    private fun onGetUserHomeSuccess(userHome: String) {
        mutableState.value = state.value.copy(userHomeDirectory = userHome)
        loadThemes()
    }

    private fun loadThemes() {
        screenModelScope.launch {
            getThemesUseCase().fold(
                onSuccess = {
                    mutableState.value = state.value.copy(
                        currentTheme = it.currentTheme,
                        themesList = it.allThemes
                    )
                    println(it)
                },
                onFailure = { println("Error getting themes: $it") }
            )
        }
    }

}