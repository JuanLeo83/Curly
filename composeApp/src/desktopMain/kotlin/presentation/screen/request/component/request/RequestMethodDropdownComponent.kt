package presentation.screen.request.component.request

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
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
import domain.model.RequestMethod

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
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { expanded = true }
                .padding(start = 16.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
        ) {
            Text(
                text = optionSelected.value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = selectColor(optionSelected)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open Menu",
                modifier = Modifier.rotate(rotation)
            )
        }

        DropdownMenu(modifier = modifier,
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            RequestMethodMenuItem(RequestMethod.GET, optionSelected, ::onClickItem)
            RequestMethodMenuItem(RequestMethod.POST, optionSelected, ::onClickItem)
            RequestMethodMenuItem(RequestMethod.PUT, optionSelected, ::onClickItem)
            RequestMethodMenuItem(RequestMethod.PATCH, optionSelected, ::onClickItem)
            RequestMethodMenuItem(RequestMethod.DELETE, optionSelected, ::onClickItem)
            RequestMethodMenuItem(RequestMethod.HEAD, optionSelected, ::onClickItem)
            RequestMethodMenuItem(RequestMethod.OPTIONS, optionSelected, ::onClickItem)
        }
    }
}

@Composable
private fun RequestMethodMenuItem(
    requestMethod: RequestMethod,
    optionSelected: RequestMethod,
    onOptionSelected: (RequestMethod) -> Unit
) {
    val backgroundColor = if (optionSelected == requestMethod) {
        Color.LightGray
    } else {
        Color.Transparent
    }

    DropdownMenuItem(
        modifier = Modifier.background(color = backgroundColor).height(32.dp),
        onClick = { onOptionSelected(requestMethod) }
    ) {
        Text(
            text = requestMethod.value,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
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