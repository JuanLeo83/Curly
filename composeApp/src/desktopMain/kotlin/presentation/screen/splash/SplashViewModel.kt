package presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.CreateConfigDirectoryUseCase
import domain.usecase.LoadCurrentThemeUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import presentation.theme.ThemeMapper
import theme

class SplashViewModel(
    private val createConfigDirectoryUseCase: CreateConfigDirectoryUseCase,
    private val loadCurrentThemeUseCase: LoadCurrentThemeUseCase,
    private val themeMapper: ThemeMapper
) : ViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            delay(3000)
            createConfigDirectoryUseCase().fold(
                onSuccess = {
                    loadCurrentThemeUseCase().fold(
                        onSuccess = {
                            theme = themeMapper.mapToTheme(it)
                            _state.value = state.value.copy(initialized = true)
                        },
                        onFailure = { println("Error loading current theme: $it") }
                    )
                },
                onFailure = { println("Error creating config directory: $it") }
            )
        }
    }
}