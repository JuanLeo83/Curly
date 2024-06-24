package presentation.screen.request.component.markuptext

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import presentation.common.Symbols.NEW_LINE
import presentation.common.Symbols.SPACE

@Composable
fun MarkupTextComponent(
    xmlString: String,
    modifier: Modifier = Modifier
) {
    val markupAnnotatedString = remember(xmlString) {
        buildAnnotatedString {
            val rows = xmlString.split(NEW_LINE)
            for (row in rows) {
                val elements = MarkupTextUtils.identifyElements(row)
                if (elements.isEmpty()) {
                    append(row)
                    append(NEW_LINE)
                } else {
                    append(MarkupTextUtils.getTabulation(row))
                    for (element in elements) {
                        withStyle(style = SpanStyle(color = getColor(element), fontWeight = FontWeight.Bold)) {
                            append(element.value)
                        }
                    }
                    append(NEW_LINE)
                }
            }
        }
    }

    Text(
        text = markupAnnotatedString,
        modifier = modifier
    )
}

private fun AnnotatedString.Builder.getColor(element: MarkupElement): Color {
    return when (element.type) {
        XmlElementType.OPEN_TAG_SYMBOL -> Color.Blue
        XmlElementType.TAG_NAME -> Color.Red
        XmlElementType.ATTRIBUTE_NAME -> {
            append(SPACE)
            Color.Green
        }

        XmlElementType.EQUAL_SYMBOL -> Color.Black
        XmlElementType.ATTRIBUTE_VALUE -> Color.Yellow
        XmlElementType.CLOSE_TAG_SYMBOL -> Color.Blue
        XmlElementType.OPEN_END_TAG_SYMBOL -> Color.Blue
        XmlElementType.END_TAG_NAME -> Color.Red
        XmlElementType.TAG_CONTENT -> Color.Black
    }
}

// https://mpec50c160a6b8c45ef0.free.beeceptor.com/data2