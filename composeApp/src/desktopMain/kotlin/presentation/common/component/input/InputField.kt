package presentation.common.component.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: () -> Unit = {},
    trailingIconVisibility: TrailingIconVisibility = TrailingIconVisibility.Always,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    val focusRequester = remember { FocusRequester() }

    Row(
        modifier = Modifier
            .size(width = 500.dp, height = 64.dp)
            .border(
                width = 1.dp,
                color = theme.colors.input.border,
                shape = RoundedCornerShape(4.dp)
            )
            .background(
                color = theme.colors.input.background,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { focusRequester.requestFocus() }
        ) {
            Text(
                text = label,
                color = theme.colors.input.label,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp)
            )


            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                maxLines = 1,
                textStyle = TextStyle(color = theme.colors.input.text, fontSize = 13.sp),
                cursorBrush = getCursorBrush(),
                visualTransformation = visualTransformation,
                decorationBox = { innerTextField ->
                    val paddingTop = if (value.isEmpty()) 0.dp else 7.dp

                    Box(
                        modifier = Modifier.padding(
                            top = paddingTop,
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 8.dp
                        ),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            innerTextField()
                            Text(
                                text = placeholder,
                                fontSize = 14.sp,
                                color = theme.colors.input.placeholder
                            )
                        } else {
                            innerTextField()
                        }
                    }
                },
                modifier = Modifier.focusRequester(focusRequester)
            )
        }

        trailingIcon?.let {
            if (trailingIconVisibility is TrailingIconVisibility.Condition && !trailingIconVisibility.condition) {
                return@let
            }
            Box(modifier = Modifier.padding(8.dp)) {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = theme.colors.input.label,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onTrailingIconClick() }
                )
            }
        }
    }
}

sealed class TrailingIconVisibility {
    data object Always : TrailingIconVisibility()
    data class Condition(
        val condition: Boolean
    ) : TrailingIconVisibility()
}