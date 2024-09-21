package presentation.screen.request.component.request.body

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Divider
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import presentation.common.Symbols.COLON
import presentation.common.Symbols.LEFT_BRACE
import presentation.common.Symbols.LEFT_BRACKET
import presentation.common.Symbols.QUOTE
import presentation.common.Symbols.RIGHT_BRACE
import presentation.common.Symbols.RIGHT_BRACKET
import presentation.common.Symbols.SPACE
import presentation.common.component.lineNumbers.LineNumbersComponent

@Composable
fun BodyJsonComponent(
    modifier: Modifier = Modifier,
    jsonValue: String,
    setJsonValue: (String) -> Unit
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(jsonValue)) }

    val jsonAnnotatedString = remember(jsonValue) {
        buildAnnotatedString {
            val regex = "(\"[^\"]*\")\\s*:\\s*|\"[^\"]*\"|\\b\\d+\\b|[{}\\[\\]]".toRegex()
            val matches = regex.findAll(jsonValue)
            var lastIndex = 0

            for (match in matches) {
                append(jsonValue.substring(lastIndex, match.range.first))
                val value = match.value
                val color = getColor(value)
                withStyle(style = SpanStyle(color = color, fontWeight = FontWeight.Light)) {
                    if (value.trim().endsWith(COLON)) {
                        append(value.dropLast(1))
                        withStyle(
                            style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Light)
                        ) {
                            append(SPACE)
                        }
                    } else {
                        append(value)
                    }
                }
                lastIndex = match.range.last + 1
            }
            append(jsonValue.substring(lastIndex))
        }
    }

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

    Box(
        modifier = modifier
            .border(1.dp, Color.Gray, MaterialTheme.shapes.small)
            .defaultMinSize(minHeight = 100.dp)
            .padding(8.dp)
    ) {
        Row {
            LineNumbersComponent(annotatedString.toString(), modifier = modifier.weight(0.05f))

            Divider(modifier = Modifier.width(1.dp).fillMaxHeight().background(Color.LightGray))

            Row(modifier = Modifier.weight(0.95f)) {
                SelectionContainer {
                    BasicTextField(
                        value = textFieldValue,
                        onValueChange = { setTextFieldValue(it) },
                        modifier = modifier.horizontalScroll(scrollState).fillMaxHeight()
                            .padding(start = 4.dp),
                        textStyle = LocalTextStyle.current.copy(color = Color.Transparent)
                    ) { innerTextField ->
                        Text(
                            annotatedString,
                            modifier = Modifier.weight(0.5f).padding(start = 4.dp)
                        )
                        innerTextField()
                    }
                }
            }
        }
    }
}

private fun getColor(value: String) = when {
    value.trim().endsWith(":") -> Color.Cyan // Keys
    value.startsWith(QUOTE) && value.endsWith(QUOTE) -> Color.Blue // String value
    value.matches("\\b\\d+\\b".toRegex()) -> Color.Red // Number value
    value == LEFT_BRACE || value == RIGHT_BRACE -> Color.Green // {}
    value == LEFT_BRACKET || value == RIGHT_BRACKET -> Color.Magenta // []
    else -> Color.Black
}