package presentation.screen.request.component.request.authorization.vo

import domain.model.AuthorizationType

data class AuthVo(
    val optionSelected: AuthorizationType = AuthorizationType.NONE,
    val onOptionSelected: (AuthorizationType) -> Unit = {},
    val basic: BasicAuthVo = BasicAuthVo(),
    val bearer: BearerAuthVo = BearerAuthVo(),
    val apiKey: ApiKeyAuthVo = ApiKeyAuthVo()
) {
    fun setBasicUserName(userName: String) = copy(basic = basic.copy(userName = userName))

    fun setBasicPassword(password: String) = copy(basic = basic.copy(password = password))

    fun setBearerToken(token: String) = copy(bearer = bearer.copy(token = token))

    fun setApiKeyAddTo(optionSelected: ApiKeyAddTo) =
        copy(apiKey = apiKey.copy(optionSelected = optionSelected))

    fun setApiKey(key: String) = copy(apiKey = apiKey.copy(key = key))

    fun setApiKeyValue(value: String) = copy(apiKey = apiKey.copy(value = value))
}