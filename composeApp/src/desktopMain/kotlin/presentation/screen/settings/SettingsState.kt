package presentation.screen.settings

data class SettingsState(
    val userHomeDirectory: String = "",
    val currentTheme: String = "",
    val newThemeLoaded: Boolean = false,
    val themesList: List<String> = emptyList()
)