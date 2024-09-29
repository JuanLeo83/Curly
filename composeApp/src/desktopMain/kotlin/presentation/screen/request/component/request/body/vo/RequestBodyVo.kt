package presentation.screen.request.component.request.body.vo

import domain.model.BodyType

data class RequestBodyVo(
    val options: List<String> = BodyType.entries.map { it.value },
    val optionSelected: BodyType = BodyType.NONE,
    val onOptionSelected: (String) -> Unit = {},
    val value: String = "",
    val setBody: (String) -> Unit = {}
)
