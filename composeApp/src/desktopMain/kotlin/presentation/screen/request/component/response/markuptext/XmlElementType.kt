package presentation.screen.request.component.response.markuptext

enum class XmlElementType {
    OPEN_TAG_SYMBOL, // <
    TAG_NAME,
    ATTRIBUTE_NAME,
    ATTRIBUTE_VALUE,
    CLOSE_TAG_SYMBOL, // > or />
    OPEN_END_TAG_SYMBOL, // </
    END_TAG_NAME,
    TAG_CONTENT
}