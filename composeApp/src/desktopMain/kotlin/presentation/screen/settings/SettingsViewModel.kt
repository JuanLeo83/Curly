package presentation.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.ApplyThemeUseCase
import domain.usecase.GetThemesUseCase
import domain.usecase.GetUserHomeUseCase
import domain.usecase.ImportThemeUseCase
import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import presentation.theme.ThemeMapper
import theme

class SettingsViewModel(
    private val getUserHomeUseCase: GetUserHomeUseCase,
    private val importThemeUseCase: ImportThemeUseCase,
    private val getThemesUseCase: GetThemesUseCase,
    private val applyThemeUseCase: ApplyThemeUseCase,
    private val themeMapper: ThemeMapper
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getUserHomeUseCase().fold(
                onSuccess = ::onGetUserHomeSuccess,
                onFailure = { println("Error getting user home directory: $it") }
            )
        }
    }

    fun importTheme(platformFile: PlatformFile?) {
        if (platformFile == null) return

        viewModelScope.launch {
            importThemeUseCase(platformFile.file.path).fold(
                onSuccess = { loadThemes() },
                onFailure = { println("Error importing theme: $it") }
            )
        }
    }

    fun applyTheme(themeName: String) {
        viewModelScope.launch {
            applyThemeUseCase(themeName).fold(
                onSuccess = {
                    theme = themeMapper.mapToTheme(it)
                    _state.value = state.value.copy(
                        currentTheme = themeName,
                        newThemeLoaded = true
                    )
                },
                onFailure = { println("Error applying theme: $it") }
            )
        }
    }

    fun resetNewThemeLoaded() {
        _state.value = state.value.copy(newThemeLoaded = false)
    }

    private fun onGetUserHomeSuccess(userHome: String) {
        _state.value = state.value.copy(userHomeDirectory = userHome)
        loadThemes()
    }

    private fun loadThemes() {
        viewModelScope.launch {
            getThemesUseCase().fold(
                onSuccess = {
                    _state.value = state.value.copy(
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