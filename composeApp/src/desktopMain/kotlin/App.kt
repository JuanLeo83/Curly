import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.screen.splash.SplashScreen
import presentation.theme.Theme

@Composable
@Preview
fun App() {
    MaterialTheme(
        colors = MaterialTheme.colors.copy()
    ) {
        Navigator(SplashScreen())
    }
}

lateinit var theme: Theme