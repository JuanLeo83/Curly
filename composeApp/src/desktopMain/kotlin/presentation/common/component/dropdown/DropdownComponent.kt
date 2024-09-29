package presentation.common.component.dropdown

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    val interactionSource = remember { MutableInteractionSource() }

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
                    interactionSource = interactionSource,
                    indication = null
                ) { expanded = true }
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = MaterialTheme.shapes.small
                )
                .padding(start = 12.dp, top = 4.dp, end = 4.dp, bottom = 4.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = optionSelected,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open Menu",
                modifier = Modifier.rotate(rotation)
            )
        }

        DropdownMenu(
            modifier = modifier,
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
    val backgroundColor = if (optionSelected == item) {
        Color.LightGray
    } else {
        Color.Transparent
    }

    DropdownMenuItem(
        modifier = Modifier.background(color = backgroundColor).height(32.dp),
        onClick = { onOptionSelected(item) }
    ) {
        Text(
            modifier = modifier,
            text = item,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}