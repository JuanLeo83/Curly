package presentation.theme

data class Theme(
    val isLight: Boolean = true,
    val colors: ThemeColor = ThemeColor(),
    val fonts: ThemeTextUnit = ThemeTextUnit()
)