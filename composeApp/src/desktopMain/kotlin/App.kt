import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.screen.request.RequestScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigator(RequestScreen())
    }
}