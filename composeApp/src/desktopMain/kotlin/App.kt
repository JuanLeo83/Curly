import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.common.component.sidebar.SidebarComponent
import presentation.screen.request.RequestScreen
import presentation.screen.settings.SettingsScreen
import presentation.screen.splash.SplashScreen
import presentation.theme.Theme

@Composable
@Preview
fun App() {
    var appState by remember { mutableStateOf(AppState()) }
    val navController: NavHostController = rememberNavController()

    fun navigate(route: Route) {
        if (appState.currentScreen == route) return

        appState = appState.copy(currentScreen = route)
        navController.navigate(route.name)
    }

    MaterialTheme(
        colors = MaterialTheme.colors.copy(
            primary = appState.theme.colors.primary,
            secondary = appState.theme.colors.secondary,
            background = appState.theme.colors.background,
            isLight = appState.theme.isLight
        )
    ) {
        Row {
            if (appState.isStarting.not()) {
                SidebarComponent(
                    appState = appState,
                    onExpandClick = {
                        appState = appState.copy(
                            isSidebarExpanded = appState.isSidebarExpanded.not()
                        )
                    },
                    navigateToRequest = { navigate(Route.Request) },
                    navigateToSettings = { navigate(Route.Settings) }
                )
            }

            NavHost(
                navController = navController,
                startDestination = Route.Splash.name,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(route = Route.Splash.name) {
                    SplashScreen(
                        onLoadTheme = { appState = appState.copy(theme = theme) },
                        navigateNext = {
                            navigate(Route.Request)
                            appState = appState.copy(isStarting = false)
                        }
                    )
                }

                composable(route = Route.Request.name) {
                    RequestScreen()
                }

                composable(route = Route.Settings.name) {
                    SettingsScreen(onLoadTheme = { appState = appState.copy(theme = theme) })
                }
            }
        }
    }
}

lateinit var theme: Theme

sealed class Route(val name: String) {
    data object Splash : Route("splash")
    data object Request : Route("request")
    data object Settings : Route("settings")
}

data class AppState(
    val theme: Theme = Theme(),
    val isStarting: Boolean = true,
    val isSidebarExpanded: Boolean = true,
    val currentScreen: Route = Route.Splash
)