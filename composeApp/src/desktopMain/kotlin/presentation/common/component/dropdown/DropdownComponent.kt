package presentation.common.component.dropdown

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme

@Composable
fun DropdownComponent(
    modifier: Modifier = Modifier,
    options: List<String>,
    optionSelected: String,
    onOptionSelected: (String) -> Unit = {}
) {
    val defaultRotationAngle = 0f
    val rotationAngle = -180f

    var expanded by remember { mutableStateOf(false) }
    val rotation: Float by animateFloatAsState(
        if (expanded) rotationAngle else defaultRotationAngle
    )
    var width by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    fun onClickItem(item: String) {
        onOptionSelected(item)
        expanded = false
    }

    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { expanded = true }
                .background(
                    color = theme.colors.input.background,
                    shape = RoundedCornerShape(4.dp)
                )
                .border(
                    width = 1.dp,
                    color = theme.colors.input.border,
                    shape = RoundedCornerShape(4.dp)
                )
                .onGloballyPositioned {
                    width = with(density) {
                        it.size.width.toDp()
                    }
                }
                .padding(start = 12.dp, top = 4.dp, end = 4.dp, bottom = 4.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = optionSelected,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = theme.colors.input.text,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open Menu",
                tint = theme.colors.input.placeholder,
                modifier = Modifier.rotate(rotation)
            )
        }

        DropdownMenu(
            modifier = modifier.background(color = theme.colors.input.background).width(width),
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            options.forEach { item ->
                MenuItemComponent(modifier, item, optionSelected, ::onClickItem)
            }
        }
    }
}

@Composable
private fun MenuItemComponent(
    modifier: Modifier = Modifier,
    item: String,
    optionSelected: String,
    onOptionSelected: (String) -> Unit
) {
    val isSelected: Boolean = optionSelected == item

    val backgroundColor = if (isSelected) {
        theme.colors.secondary.copy(alpha = 0.1f)
    } else {
        Color.Transparent
    }

    DropdownMenuItem(
        modifier = Modifier.background(color = backgroundColor).height(32.dp),
        onClick = { onOptionSelected(item) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = theme.colors.input.text,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Option selected",
                    tint = theme.colors.table.header.text,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}