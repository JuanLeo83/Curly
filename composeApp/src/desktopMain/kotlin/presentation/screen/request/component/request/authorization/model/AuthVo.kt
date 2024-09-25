package presentation.screen.request.component.request.authorization.model

data class AuthVo(
    val basic: BasicAuthVo = BasicAuthVo(),
    val bearer: BearerAuthVo = BearerAuthVo(),
    val apiKey: ApiKeyAuthVo = ApiKeyAuthVo()
)