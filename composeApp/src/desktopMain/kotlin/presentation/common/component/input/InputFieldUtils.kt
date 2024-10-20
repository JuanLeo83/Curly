package presentation.common.component.input

import androidx.compose.ui.graphics.SolidColor
import theme

fun getCursorBrush() = SolidColor(if (theme.isLight) theme.colors.primary else theme.colors.secondary)