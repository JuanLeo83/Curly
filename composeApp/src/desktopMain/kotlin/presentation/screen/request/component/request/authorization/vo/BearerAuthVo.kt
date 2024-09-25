package presentation.screen.request.component.request.authorization.vo

data class BearerAuthVo(
    val token: String = "",
    val onTokenChange: (String) -> Unit = {}
)
