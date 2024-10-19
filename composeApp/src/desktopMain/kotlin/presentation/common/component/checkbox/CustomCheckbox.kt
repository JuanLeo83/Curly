package presentation.common.component.checkbox

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import theme


@Composable
fun CustomCheckbox(
    enabled: Boolean,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(modifier = Modifier.size(18.dp)
        .border(
            width = 1.dp,
            color = getBorderColor(enabled, checked),
            shape = RoundedCornerShape(4.dp)
        )
        .background(
            color = getBackgroundColor(enabled, checked),
            shape = RoundedCornerShape(4.dp)
        )
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { if (enabled) onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = getIconColor(enabled)
            )
        }
    }
}

private fun getBorderColor(enabled: Boolean, checked: Boolean): Color {
    return when {
        checked && enabled -> theme.colors.checkbox.enabled.checked.border
        checked -> theme.colors.checkbox.disabled.checked.border
        enabled -> theme.colors.checkbox.enabled.border
        else -> theme.colors.checkbox.disabled.border
    }
}

private fun getBackgroundColor(enabled: Boolean, checked: Boolean): Color {
    return when {
        enabled && checked -> theme.colors.checkbox.enabled.checked.background
        enabled -> theme.colors.checkbox.enabled.background
        checked -> theme.colors.checkbox.disabled.checked.background
        else -> theme.colors.checkbox.disabled.background
    }
}

private fun getIconColor(enabled: Boolean): Color {
    return if (enabled) theme.colors.checkbox.enabled.checked.icon
    else theme.colors.checkbox.disabled.checked.icon
}