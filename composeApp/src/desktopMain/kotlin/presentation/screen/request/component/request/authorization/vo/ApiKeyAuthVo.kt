package presentation.screen.request.component.request.authorization.vo

import domain.model.ApiKeyAddTo

data class ApiKeyAuthVo(
    val options: List<String> = ApiKeyAddTo.entries.map { it.value },
    val optionSelected: ApiKeyAddTo = ApiKeyAddTo.HEADERS,
    val onOptionSelected: (String) -> Unit = {},
    val key: String = "",
    val onKeyChange: (String) -> Unit = {},
    val value: String = "",
    val onValueChange: (String) -> Unit = {}
)
