package presentation.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType
import org.koin.compose.viewmodel.koinViewModel
import presentation.common.component.dropdown.DropdownComponent
import theme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
    onLoadTheme: () -> Unit
) {

    val state by viewModel.state.collectAsState()

    val launcher = rememberFilePickerLauncher(
        type = PickerType.File(extensions = listOf(JSON)),
        mode = PickerMode.Single,
        title = "Pick a theme file",
        initialDirectory = state.userHomeDirectory
    ) { file ->
        viewModel.importTheme(file)
    }

    if (state.newThemeLoaded) {
        onLoadTheme()
        viewModel.resetNewThemeLoaded()
    }

    Box(Modifier.fillMaxSize().background(color = theme.colors.background).padding(16.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Button(onClick = { launcher.launch() }) {
                Text("Import theme")
            }

            Spacer(modifier = Modifier.height(16.dp))

            DropdownComponent(
                modifier = Modifier.width(200.dp),
                options = state.themesList,
                optionSelected = state.currentTheme,
            ) { themeName ->
                viewModel.applyTheme(themeName)
            }
        }
    }
}

private const val JSON = "json"