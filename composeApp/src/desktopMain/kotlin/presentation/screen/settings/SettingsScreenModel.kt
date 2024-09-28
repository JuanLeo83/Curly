package presentation.screen.settings

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.usecase.GetUserHomeUseCase
import domain.usecase.ImportThemeUseCase
import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.coroutines.launch

class SettingsScreenModel(
    private val getUserHomeUseCase: GetUserHomeUseCase,
    private val importThemeUseCase: ImportThemeUseCase
) : StateScreenModel<SettingsScreenState>(SettingsScreenState()) {

    init {
        screenModelScope.launch {
            getUserHomeUseCase().fold(
                onSuccess = { mutableState.value = state.value.copy(userHomeDirectory = it) },
                onFailure = { println("Error getting user home directory: $it") }
            )
        }
    }

    fun importTheme(platformFile: PlatformFile?) {
        if (platformFile == null) return

        importThemeUseCase(platformFile.file.path).fold(
            onSuccess = {},
            onFailure = { println("Error importing theme: $it") }
        )
    }

}