import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.screen.splash.SplashScreen
import presentation.theme.Theme

@Composable
@Preview
fun App() {
    var appTheme by remember { mutableStateOf(Theme()) }

    MaterialTheme(
        colors = MaterialTheme.colors.copy(
            primary = appTheme.colors.primary,
            secondary = appTheme.colors.secondary,
            background = appTheme.colors.background,
            isLight = appTheme.isLight
        )
    ) {
        Navigator(SplashScreen(onLoadTheme = { appTheme = theme }))
    }
}

lateinit var theme: Theme