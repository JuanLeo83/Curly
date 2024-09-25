package presentation.screen.request.component.request.authorization

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.AuthorizationType
import presentation.screen.request.component.request.authorization.vo.AuthVo

@Composable
fun RequestAuthorizationComponent(authVo: AuthVo) {
    Row {
        AuthorizationTypeDropdownComponent(
            optionSelected = authVo.optionSelected
        ) { authVo.onOptionSelected(it) }

        Spacer(modifier = Modifier.width(8.dp))

        AuthenticationComponentSelector(
            optionSelected = authVo.optionSelected,
            authVo = authVo
        )
    }
}

@Composable
private fun AuthenticationComponentSelector(
    optionSelected: AuthorizationType,
    authVo: AuthVo
) {
    when (optionSelected) {
        AuthorizationType.BASIC -> BasicAuthComponent(vo = authVo.basic)
        AuthorizationType.BEARER -> BearerAuthComponent(vo = authVo.bearer)
        AuthorizationType.API_KEY -> ApiKeyAuthComponent(vo = authVo.apiKey)

        AuthorizationType.OAUTH2 -> Text("Coming soon")
        AuthorizationType.NONE -> {}
    }
}