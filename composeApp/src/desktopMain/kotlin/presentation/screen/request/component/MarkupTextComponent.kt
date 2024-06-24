package presentation.screen.request.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import presentation.common.Symbols.ATTRIBUTE_SEPARATOR
import presentation.common.Symbols.AUTO_CLOSE_TAG_SYMBOL
import presentation.common.Symbols.CLOSE_TAG_SYMBOL
import presentation.common.Symbols.NEW_LINE
import presentation.common.Symbols.OPEN_END_TAG_SYMBOL
import presentation.common.Symbols.OPEN_TAG_SYMBOL
import presentation.common.Symbols.QUOTE
import presentation.common.Symbols.SPACE

enum class XmlElementType {
    OPEN_TAG_SYMBOL, // El símbolo < al comienzo de la fila
    TAG_NAME, // El nombre de una etiqueta XML
    ATTRIBUTE_NAME, // El nombre de un atributo
    EQUAL_SYMBOL, // El símbolo igual
    ATTRIBUTE_VALUE, // El valor de un atributo
    CLOSE_TAG_SYMBOL, // El símbolo > o />
    OPEN_END_TAG_SYMBOL, // El símbolo </
    END_TAG_NAME, // El nombre de una etiqueta de cierre
    TAG_CONTENT // El contenido de una etiqueta
}

fun identifyElements(row: String): List<XmlElement> {
    val elements = mutableListOf<XmlElement>()

    val pattern = "(</|<|>|/>|\"[^\"]*\"|\\s+\\w+=|[^<>\"]+)".toRegex()
    val matches = pattern.findAll(row)

    for (match in matches) {
        val value = match.value.trim()
        val type = when {
            value.startsWith(OPEN_END_TAG_SYMBOL) -> XmlElementType.OPEN_END_TAG_SYMBOL
            value.startsWith(OPEN_TAG_SYMBOL) -> XmlElementType.OPEN_TAG_SYMBOL
            value.startsWith(CLOSE_TAG_SYMBOL) -> XmlElementType.CLOSE_TAG_SYMBOL
            value.endsWith(AUTO_CLOSE_TAG_SYMBOL) -> XmlElementType.CLOSE_TAG_SYMBOL
            value.startsWith(QUOTE) -> XmlElementType.ATTRIBUTE_VALUE
            value.contains(ATTRIBUTE_SEPARATOR) -> {
                val parts = value.split(SPACE)
                if (parts.size > 1) {
                    elements.add(XmlElement(XmlElementType.TAG_NAME, parts[0].trim()))
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
            elements.add(XmlElement(type, if (split.size > 1) split[1] else split[0]))
        } else {
            elements.add(XmlElement(type, value))
        }
    }

    return elements
}

data class XmlElement(val type: XmlElementType, val value: String)


fun getTabulation(row: String): String {
    val tabulation = StringBuilder()
    for (char in row) {
        if (char == ' ') {
            tabulation.append(' ')
        } else break
    }
    return tabulation.toString()
}

@Composable
fun MarkupTextComponent(
    xmlString: String,
    modifier: Modifier = Modifier
) {

    println(xmlString)
    val xmlAnnotatedString = remember(xmlString) {
        buildAnnotatedString {
            var lastIndex = 0

            val rows = xmlString.split(NEW_LINE)
            for (row in rows) {
                val elements = identifyElements(row)
                if (elements.isEmpty()) {
                    append(row)
                    append(NEW_LINE)
                    lastIndex += row.length + 1
                } else {
                    append(getTabulation(row))
                    for (element in elements) {
                        val color = when (element.type) {
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

                        withStyle(style = SpanStyle(color = color, fontWeight = FontWeight.Bold)) {
                            append(element.value)
                        }

                        lastIndex += element.value.length
                    }
                    append(NEW_LINE)
                }
            }
        }
    }

    Text(
        text = xmlAnnotatedString,
        modifier = modifier
    )
}

// https://mpec50c160a6b8c45ef0.free.beeceptor.com/data2