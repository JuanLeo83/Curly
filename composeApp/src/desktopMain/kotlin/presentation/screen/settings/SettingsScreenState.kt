package presentation.screen.settings

data class SettingsScreenState(
    val userHomeDirectory: String = "",
    val currentTheme: String = "",
    val newThemeLoaded: Boolean = false,
    val themesList: List<String> = emptyList()
)