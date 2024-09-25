package presentation.screen.request.component.request.authorization.model

data class BasicAuthVo(
    val userName: String = "",
    val password: String = "",
    val onUserNameChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {}
)
