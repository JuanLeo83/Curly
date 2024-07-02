package presentation.screen.request.component.response.json

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import presentation.common.Symbols.COLON
import presentation.common.Symbols.LEFT_BRACE
import presentation.common.Symbols.LEFT_BRACKET
import presentation.common.Symbols.QUOTE
import presentation.common.Symbols.RIGHT_BRACE
import presentation.common.Symbols.RIGHT_BRACKET
import presentation.common.Symbols.SPACE
import presentation.common.component.textWithLineNumbers.TextWithLineNumbers


@Composable
fun JsonText(
    jsonString: String,
    modifier: Modifier = Modifier
) {
    val jsonAnnotatedString = remember(jsonString) {
        buildAnnotatedString {
            val regex = "(\"[^\"]*\")\\s*:\\s*|\"[^\"]*\"|\\b\\d+\\b|[{}\\[\\]]".toRegex()
            val matches = regex.findAll(jsonString)
            var lastIndex = 0

            for (match in matches) {
                append(jsonString.substring(lastIndex, match.range.first))
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
            append(jsonString.substring(lastIndex))
        }
    }

    TextWithLineNumbers(jsonAnnotatedString, modifier = modifier)
}

private fun getColor(value: String) = when {
    value.trim().endsWith(":") -> Color.Cyan // Keys
    value.startsWith(QUOTE) && value.endsWith(QUOTE) -> Color.Blue // String value
    value.matches("\\b\\d+\\b".toRegex()) -> Color.Red // Number value
    value == LEFT_BRACE || value == RIGHT_BRACE -> Color.Green // {}
    value == LEFT_BRACKET || value == RIGHT_BRACKET -> Color.Magenta // []
    else -> Color.Black
}