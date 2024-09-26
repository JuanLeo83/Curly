package presentation.screen.request.component.request.authorization.vo

import domain.model.ApiKeyAddTo

data class ApiKeyAuthVo(
    val optionSelected: ApiKeyAddTo = ApiKeyAddTo.HEADERS,
    val onOptionSelected: (ApiKeyAddTo) -> Unit = {},
    val key: String = "",
    val onKeyChange: (String) -> Unit = {},
    val value: String = "",
    val onValueChange: (String) -> Unit = {}
)
