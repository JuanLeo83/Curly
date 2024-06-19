package presentation.screen.request.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import domain.model.RequestMethod

@Composable
fun RequestMethodDropdownComponent(
    modifier: Modifier = Modifier,
    optionSelected: RequestMethod = RequestMethod.GET,
    onOptionSelected: (RequestMethod) -> Unit
) {
    val defaultRotationAngle = 0f
    val rotationAngle = -180f

    var expanded by remember { mutableStateOf(false) }
    val rotation: Float by animateFloatAsState(
        if (expanded) rotationAngle else defaultRotationAngle
    )

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .width(150.dp)
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = MaterialTheme.shapes.small
                ).clickable { expanded = true }
                .padding(8.dp)
        ) {
            Text(text = optionSelected.value, color = selectColor(optionSelected))
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open Menu",
                modifier = Modifier.rotate(rotation)
            )
        }

        DropdownMenu(modifier = modifier,
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            RequestMethodMenuItem(RequestMethod.GET) { method ->
                onOptionSelected(method)
                expanded = false
            }

            RequestMethodMenuItem(RequestMethod.POST) { method ->
                onOptionSelected(method)
                expanded = false
            }

            RequestMethodMenuItem(RequestMethod.PUT) { method ->
                onOptionSelected(method)
                expanded = false
            }

            RequestMethodMenuItem(RequestMethod.PATCH) { method ->
                onOptionSelected(method)
                expanded = false
            }

            RequestMethodMenuItem(RequestMethod.DELETE) { method ->
                onOptionSelected(method)
                expanded = false
            }

            RequestMethodMenuItem(RequestMethod.HEAD) { method ->
                onOptionSelected(method)
                expanded = false
            }

            RequestMethodMenuItem(RequestMethod.OPTIONS) { method ->
                onOptionSelected(method)
                expanded = false
            }
        }
    }
}

@Composable
private fun RequestMethodMenuItem(
    requestMethod: RequestMethod,
    onOptionSelected: (RequestMethod) -> Unit
) {
    DropdownMenuItem(onClick = {
        onOptionSelected(requestMethod)
    }) {
        Text(
            text = requestMethod.value,
            color = selectColor(requestMethod)
        )
    }
}

private fun selectColor(option: RequestMethod): Color = when (option) {
    RequestMethod.GET -> Color.Green
    RequestMethod.POST -> Color.Yellow
    RequestMethod.PUT -> Color.Blue
    RequestMethod.PATCH -> Color.Red
    RequestMethod.DELETE -> Color.Magenta
    RequestMethod.HEAD -> Color.Cyan
    RequestMethod.OPTIONS -> Color.Gray
}