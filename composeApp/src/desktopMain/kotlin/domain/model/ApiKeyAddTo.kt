package domain.model

enum class ApiKeyAddTo(val value: String) {
    HEADERS("Headers"),
    QUERY_PARAMETERS("Query Params")
}