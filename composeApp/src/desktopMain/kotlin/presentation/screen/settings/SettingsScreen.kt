package presentation.screen.settings

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType

class SettingsScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<SettingsScreenModel>()
        val state by screenModel.state.collectAsState()

        val launcher = rememberFilePickerLauncher(
            type = PickerType.File(extensions = listOf(JSON)),
            mode = PickerMode.Single,
            title = "Pick a theme file",
            initialDirectory = state.userHomeDirectory
        ) { file ->
            screenModel.importTheme(file)
        }

        Button(onClick = { launcher.launch() }) {
            Text("Import theme")
        }
    }

    companion object {
        private const val JSON = "json"
    }
}