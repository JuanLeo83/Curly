package presentation.screen.request.component.request.body.vo

import domain.model.BodyType

data class RequestBodyVo(
    val optionSelected: BodyType = BodyType.NONE,
    val onOptionSelected: (BodyType) -> Unit = {},
    val value: String = "",
    val setBody: (String) -> Unit = {}
)
