package presentation.screen.settings

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.usecase.ApplyThemeUseCase
import domain.usecase.GetThemesUseCase
import domain.usecase.GetUserHomeUseCase
import domain.usecase.ImportThemeUseCase
import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.coroutines.launch

class SettingsScreenModel(
    private val getUserHomeUseCase: GetUserHomeUseCase,
    private val importThemeUseCase: ImportThemeUseCase,
    private val getThemesUseCase: GetThemesUseCase,
    private val applyThemeUseCase: ApplyThemeUseCase
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

        importThemeUseCase(platformFile.file.path).fold(
            onSuccess = { loadThemes() },
            onFailure = { println("Error importing theme: $it") }
        )
    }

    fun applyTheme(themeName: String) {
        screenModelScope.launch {
            applyThemeUseCase(themeName).fold(
                onSuccess = { println("Theme applied: ${it.name}") }, // TODO: Apply theme
                onFailure = { println("Error applying theme: $it") }
            )
        }
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