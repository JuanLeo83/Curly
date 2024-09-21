package domain.model

enum class BodyType(val value: String) {
    NONE("None"),
    JSON("Json"),
    XML("Xml"),
    HTML("Html"),
    TEXT("Text")
}