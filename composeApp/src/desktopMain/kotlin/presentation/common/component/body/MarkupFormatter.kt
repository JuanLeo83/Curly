package presentation.common.component.body

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import presentation.common.Symbols
import presentation.common.Symbols.NEW_LINE
import presentation.common.Symbols.SPACE
import theme

@Composable
fun MarkupFormatter(markupValue: String) = remember(markupValue) {
    buildAnnotatedString {
        val rows = markupValue.split(NEW_LINE)
        for (row in rows) {
            val elements = identifyElements(row)
            if (elements.isEmpty()) {
                append(row)
                append(NEW_LINE)
            } else {
                append(getTabulation(row))
                for (element in elements) {
                    withStyle(
                        style = SpanStyle(
                            color = getColor(element),
                            fontWeight = getFontWeight(element)
                        )
                    ) {
                        append(element.value)
                    }
                }
                append(NEW_LINE)
            }
        }
    }
}

private fun AnnotatedString.Builder.getColor(element: MarkupElement): Color {
    return when (element.type) {
        XmlElementType.OPEN_TAG_SYMBOL -> theme.colors.syntax.markup.tag
        XmlElementType.TAG_NAME -> theme.colors.syntax.markup.tagName
        XmlElementType.ATTRIBUTE_NAME -> {
            append(SPACE)
            theme.colors.syntax.markup.attributeName
        }

        XmlElementType.ATTRIBUTE_VALUE -> theme.colors.syntax.markup.attributeValue
        XmlElementType.CLOSE_TAG_SYMBOL -> theme.colors.syntax.markup.tag
        XmlElementType.OPEN_END_TAG_SYMBOL -> theme.colors.syntax.markup.tag
        XmlElementType.END_TAG_NAME -> theme.colors.syntax.markup.tag
        XmlElementType.TAG_CONTENT -> theme.colors.syntax.markup.content
    }
}

private fun getFontWeight(element: MarkupElement): FontWeight {
    return when (element.type) {
        XmlElementType.OPEN_TAG_SYMBOL,
        XmlElementType.TAG_NAME,
        XmlElementType.OPEN_END_TAG_SYMBOL,
        XmlElementType.CLOSE_TAG_SYMBOL-> FontWeight.Bold
        else -> FontWeight.Light
    }
}

private fun identifyElements(row: String): List<MarkupElement> {
    val elements = mutableListOf<MarkupElement>()

    val pattern = "(</|<|>|/>|\"[^\"]*\"|\\s+\\w+=|[^<>\"]+)".toRegex()
    val matches = pattern.findAll(row)

    for (match in matches) {
        val value = match.value.trim()
        val type = when {
            value.startsWith(Symbols.OPEN_END_TAG_SYMBOL) -> XmlElementType.OPEN_END_TAG_SYMBOL
            value.startsWith(Symbols.OPEN_TAG_SYMBOL) -> XmlElementType.OPEN_TAG_SYMBOL
            value.startsWith(Symbols.CLOSE_TAG_SYMBOL) -> XmlElementType.CLOSE_TAG_SYMBOL
            value.endsWith(Symbols.AUTO_CLOSE_TAG_SYMBOL) -> XmlElementType.CLOSE_TAG_SYMBOL
            value.startsWith(Symbols.QUOTE) -> XmlElementType.ATTRIBUTE_VALUE
            value.contains(Symbols.ATTRIBUTE_SEPARATOR) -> {
                val parts = value.split(SPACE)
                if (parts.size > 1) {
                    elements.add(MarkupElement(XmlElementType.TAG_NAME, parts[0].trim()))
                    XmlElementType.ATTRIBUTE_NAME
                } else XmlElementType.ATTRIBUTE_NAME
            }

            else -> {
                if (elements.isNotEmpty() &&
                    (elements.last().type == XmlElementType.OPEN_END_TAG_SYMBOL ||
                            elements.last().type == XmlElementType.OPEN_TAG_SYMBOL)
                ) {
                    XmlElementType.TAG_NAME
                } else {
                    XmlElementType.TAG_CONTENT
                }
            }
        }
        if (type == XmlElementType.ATTRIBUTE_NAME) {
            val split = value.split(SPACE)
            elements.add(MarkupElement(type, if (split.size > 1) split[1] else split[0]))
        } else {
            elements.add(MarkupElement(type, value))
        }
    }

    return elements
}

private fun getTabulation(row: String): String {
    val tabulation = StringBuilder()
    for (char in row) {
        if (char == ' ') {
            tabulation.append(' ')
        } else break
    }
    return tabulation.toString()
}

private data class MarkupElement(val type: XmlElementType, val value: String)

private enum class XmlElementType {
    OPEN_TAG_SYMBOL, // <
    TAG_NAME,
    ATTRIBUTE_NAME,
    ATTRIBUTE_VALUE,
    CLOSE_TAG_SYMBOL, // > or />
    OPEN_END_TAG_SYMBOL, // </
    END_TAG_NAME,
    TAG_CONTENT
}