package presentation.screen.request.component.request.authorization.vo

import domain.model.AuthorizationType

data class AuthVo(
    val optionSelected: AuthorizationType = AuthorizationType.NONE,
    val onOptionSelected: (AuthorizationType) -> Unit = {},
    val basic: BasicAuthVo = BasicAuthVo(),
    val bearer: BearerAuthVo = BearerAuthVo(),
    val apiKey: ApiKeyAuthVo = ApiKeyAuthVo()
)