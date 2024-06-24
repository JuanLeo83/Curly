package presentation.screen.request.component.markuptext

import presentation.common.Symbols

internal object MarkupTextUtils {
    fun identifyElements(row: String): List<MarkupElement> {
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
                    val parts = value.split(Symbols.SPACE)
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
                val split = value.split(Symbols.SPACE)
                elements.add(MarkupElement(type, if (split.size > 1) split[1] else split[0]))
            } else {
                elements.add(MarkupElement(type, value))
            }
        }

        return elements
    }

    fun getTabulation(row: String): String {
        val tabulation = StringBuilder()
        for (char in row) {
            if (char == ' ') {
                tabulation.append(' ')
            } else break
        }
        return tabulation.toString()
    }
}