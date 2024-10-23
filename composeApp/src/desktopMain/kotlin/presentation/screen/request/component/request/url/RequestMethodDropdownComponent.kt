package presentation.screen.request.component.request.url

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import domain.model.RequestMethod
import theme

@Composable
fun RequestMethodDropdownComponent(
    modifier: Modifier = Modifier,
    optionSelected: RequestMethod = RequestMethod.GET,
    onOptionSelected: (RequestMethod) -> Unit = {}
) {
    val defaultRotationAngle = 0f
    val rotationAngle = -180f

    var expanded by remember { mutableStateOf(false) }
    val rotation: Float by animateFloatAsState(
        if (expanded) rotationAngle else defaultRotationAngle
    )
    val interactionSource = remember { MutableInteractionSource() }
    var width by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    fun onClickItem(method: RequestMethod) {
        onOptionSelected(method)
        expanded = false
    }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .width(150.dp)
                .onGloballyPositioned {
                    width = with(density) {
                        it.size.width.toDp()
                    }
                }
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { expanded = true }
                .padding(start = 16.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
        ) {
            Text(
                text = optionSelected.value,
                fontSize = theme.fonts.requestTypes.optionSelected,
                fontWeight = FontWeight.Bold,
                color = selectColor(optionSelected)
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
            RequestMethod.entries.forEach { requestMethod ->
                RequestMethodMenuItem(requestMethod, optionSelected, ::onClickItem)
            }
        }
    }
}

@Composable
private fun RequestMethodMenuItem(
    requestMethod: RequestMethod,
    optionSelected: RequestMethod,
    onOptionSelected: (RequestMethod) -> Unit
) {
    val isSelected: Boolean = optionSelected == requestMethod

    val backgroundColor = if (isSelected) {
        theme.colors.secondary.copy(alpha = 0.1f)
    } else {
        Color.Transparent
    }

    val contentColor = selectColor(requestMethod)

    DropdownMenuItem(
        modifier = Modifier.background(color = backgroundColor).height(32.dp),
        onClick = { onOptionSelected(requestMethod) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = requestMethod.value,
                fontSize = theme.fonts.requestTypes.option,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected Request Method",
                    tint = contentColor,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

    }
}

private fun selectColor(option: RequestMethod): Color = when (option) {
    RequestMethod.GET -> theme.colors.requestTypes.getRequest
    RequestMethod.POST -> theme.colors.requestTypes.postRequest
    RequestMethod.PUT -> theme.colors.requestTypes.putRequest
    RequestMethod.PATCH -> theme.colors.requestTypes.patchRequest
    RequestMethod.DELETE -> theme.colors.requestTypes.deleteRequest
    RequestMethod.HEAD -> theme.colors.requestTypes.headRequest
    RequestMethod.OPTIONS -> theme.colors.requestTypes.optionsRequest
}