package presentation.screen.request.component.request.authorization.model

data class ApiKeyAuthVo(
    val optionSelected: ApiKeyAddTo = ApiKeyAddTo.HEADERS,
    val onOptionSelected: (ApiKeyAddTo) -> Unit = {},
    val key: String = "",
    val onKeyChange: (String) -> Unit = {},
    val value: String = "",
    val onValueChange: (String) -> Unit = {}
)

enum class ApiKeyAddTo(val value: String) {
    HEADERS("Headers"),
    QUERY_PARAMETERS("Query Params")
}
