package presentation.common.component.body

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import presentation.common.Symbols.COLON
import presentation.common.Symbols.COMMA
import presentation.common.Symbols.LEFT_BRACE
import presentation.common.Symbols.LEFT_BRACKET
import presentation.common.Symbols.QUOTE
import presentation.common.Symbols.RIGHT_BRACE
import presentation.common.Symbols.RIGHT_BRACKET
import presentation.common.Symbols.SPACE
import theme

@Composable
fun JsonFormatter(jsonValue: String) = remember(jsonValue) {
    buildAnnotatedString {
        val regex = "(\"[^\"]*\")\\s*:\\s*|\"[^\"]*\"|\\b\\d+(\\.\\d+)?\\b|[{}\\[\\],]|\\btrue\\b|\\bfalse\\b|\\bnull\\b".toRegex()
        val matches = regex.findAll(jsonValue)
        var lastIndex = 0

        for (match in matches) {
            append(jsonValue.substring(lastIndex, match.range.first))
            val value = match.value
            val color = getColor(value)
            withStyle(style = SpanStyle(color = color, fontWeight = getFontWeight(value))) {
                if (value.trim().endsWith(COLON)) {
                    append(value.dropLast(1))
                    withStyle(
                        style = SpanStyle(
                            color = theme.colors.input.placeholder,
                            fontWeight = FontWeight.Light
                        )
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

fun getColor(value: String) = when {
    value.trim().endsWith(COLON) -> theme.colors.syntax.json.key // Keys
    value == COMMA -> theme.colors.input.placeholder // Comma
    value.startsWith(QUOTE) && value.endsWith(QUOTE) -> theme.colors.syntax.json.stringValue // String value
    value.matches("\\b\\d+(\\.\\d+)?\\b".toRegex()) -> theme.colors.syntax.json.numberValue // Number value
    value == LEFT_BRACE || value == RIGHT_BRACE -> theme.colors.syntax.json.curlyBraces // {}
    value == LEFT_BRACKET || value == RIGHT_BRACKET -> theme.colors.syntax.json.brackets // []
    value == "true" || value == "false" -> theme.colors.syntax.json.booleanValue // Boolean value
    value == "null" -> theme.colors.syntax.json.nullValue // Null value
    else -> theme.colors.input.placeholder
}

fun getFontWeight(value: String): FontWeight = when {
    value == "true" || value == "false" || value == "null" -> FontWeight.Bold
    else -> FontWeight.Light
}