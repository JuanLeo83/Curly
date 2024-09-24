package domain.model

enum class AuthorizationType(val value: String) {
    NONE("None"),
    BASIC("Basic"),
    BEARER("Bearer"),
    API_KEY("API Key"),
    OAUTH2("OAuth 2.0")
}