package presentation.common.component.body

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import presentation.common.Symbols.NEW_LINE
import presentation.common.Symbols.SPACE
import presentation.screen.request.component.response.markuptext.MarkupElement
import presentation.screen.request.component.response.markuptext.MarkupTextUtils
import presentation.screen.request.component.response.markuptext.XmlElementType
import theme

@Composable
fun MarkupFormatter(markupValue: String) = remember(markupValue) {
    buildAnnotatedString {
        val rows = markupValue.split(NEW_LINE)
        for (row in rows) {
            val elements = MarkupTextUtils.identifyElements(row)
            if (elements.isEmpty()) {
                append(row)
                append(NEW_LINE)
            } else {
                append(MarkupTextUtils.getTabulation(row))
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