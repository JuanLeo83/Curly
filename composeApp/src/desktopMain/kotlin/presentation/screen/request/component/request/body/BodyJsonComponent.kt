package presentation.screen.request.component.request.body

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.common.component.body.JsonFormatter
import presentation.common.component.input.getCursorBrush
import presentation.common.component.lineNumbers.LineNumbersComponent
import theme

@Composable
fun BodyJsonComponent(
    modifier: Modifier = Modifier,
    jsonValue: String,
    setJsonValue: (String) -> Unit
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(jsonValue)) }
    val jsonAnnotatedString = JsonFormatter(jsonValue)

    JsonFormWithLineNumbers(modifier, jsonAnnotatedString, textFieldValue) {
        textFieldValue = it
        setJsonValue(it.text)
    }
}

@Composable
fun JsonFormWithLineNumbers(
    modifier: Modifier = Modifier,
    annotatedString: AnnotatedString,
    textFieldValue: TextFieldValue,
    setTextFieldValue: (TextFieldValue) -> Unit
) {
    val scrollState = rememberScrollState()
    var height by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = modifier
            .border(1.dp, theme.colors.input.border, RoundedCornerShape(4.dp))
            .defaultMinSize(minHeight = 100.dp)
            .onGloballyPositioned {
                height = with(density) {
                    it.size.height.toDp()
                }
            }
            .background(theme.colors.input.background)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { focusRequester.requestFocus() }
    ) {
        Row(modifier = Modifier.verticalScroll(scrollState)) {
            LineNumbersComponent(annotatedString.toString(), modifier = modifier.weight(0.05f))

            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .height(height)
                    .background(theme.colors.input.border)
            )

            Row(modifier = Modifier.weight(0.95f)) {
                BasicTextField(
                    value = textFieldValue,
                    onValueChange = { setTextFieldValue(it) },
                    modifier = modifier
                        .horizontalScroll(scrollState)
                        .fillMaxHeight()
                        .focusRequester(focusRequester)
                        .padding(vertical = 10.dp, horizontal = 4.dp),
                    textStyle = LocalTextStyle.current.copy(
                        color = Color.Transparent,
                        fontSize = 14.sp
                    ),
                    cursorBrush = getCursorBrush(),
                ) { innerTextField ->
                    Text(
                        text = annotatedString,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(0.5f)
                    )
                    innerTextField()
                }
            }
        }
    }
}