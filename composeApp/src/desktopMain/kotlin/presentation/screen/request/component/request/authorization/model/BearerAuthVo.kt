package presentation.screen.request.component.request.authorization.model

data class BearerAuthVo(
    val token: String = "",
    val onTokenChange: (String) -> Unit = {}
)
