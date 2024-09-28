package presentation.screen.settings

data class SettingsScreenState(
    val userHomeDirectory: String = "",
    val currentTheme: String = "",
    val themesList: List<String> = emptyList()
)