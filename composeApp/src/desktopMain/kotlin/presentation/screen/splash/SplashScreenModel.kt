package presentation.screen.splash

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.usecase.CreateConfigDirectoryUseCase
import domain.usecase.LoadCurrentThemeUseCase
import kotlinx.coroutines.launch
import presentation.theme.ThemeMapper
import theme

class SplashScreenModel(
    private val createConfigDirectoryUseCase: CreateConfigDirectoryUseCase,
    private val loadCurrentThemeUseCase: LoadCurrentThemeUseCase,
    private val themeMapper: ThemeMapper
) : StateScreenModel<SplashScreenState>(SplashScreenState()) {

    init {
        screenModelScope.launch {
            createConfigDirectoryUseCase().fold(
                onSuccess = {
                    loadCurrentThemeUseCase().fold(
                        onSuccess = {
                            theme = themeMapper.mapToTheme(it)
                            mutableState.value = state.value.copy(initialized = true)
                        },
                        onFailure = { println("Error loading current theme: $it") }
                    )
                },
                onFailure = { println("Error creating config directory: $it") }
            )
        }
    }
}