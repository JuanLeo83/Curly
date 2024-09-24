package presentation.screen.request.component.request.body

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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.BodyType

@Composable
fun BodyTypeDropdownComponent(
    modifier: Modifier = Modifier,
    optionSelected: BodyType = BodyType.NONE,
    onOptionSelected: (BodyType) -> Unit
) {
    val defaultRotationAngle = 0f
    val rotationAngle = -180f

    var expanded by remember { mutableStateOf(false) }
    val rotation: Float by animateFloatAsState(
        if (expanded) rotationAngle else defaultRotationAngle
    )
    val interactionSource = remember { MutableInteractionSource() }

    fun onClickItem(method: BodyType) {
        onOptionSelected(method)
        expanded = false
    }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .width(150.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { expanded = true }
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = MaterialTheme.shapes.small
                )
                .padding(start = 16.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)

        ) {
            Text(
                text = optionSelected.value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
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
            onDismissRequest = { expanded = false }
        ) {
            BodyType.entries.forEach { bodyType ->
                BodyTypeMenuItem(
                    bodyType = bodyType,
                    optionSelected = optionSelected,
                    onOptionSelected = ::onClickItem
                )
            }
        }
    }
}

@Composable
fun BodyTypeMenuItem(
    bodyType: BodyType,
    optionSelected: BodyType,
    onOptionSelected: (BodyType) -> Unit
) {
    val backgroundColor = if (optionSelected == bodyType) {
        Color.LightGray
    } else {
        Color.Transparent
    }

    DropdownMenuItem(
        modifier = Modifier.background(color = backgroundColor).height(32.dp),
        onClick = { onOptionSelected(bodyType) }
    ) {
        Text(
            text = bodyType.value,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}