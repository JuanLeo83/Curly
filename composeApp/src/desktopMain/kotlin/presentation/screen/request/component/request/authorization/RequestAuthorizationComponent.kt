package presentation.screen.request.component.request.authorization

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.AuthorizationType
import presentation.common.component.dropdown.DropdownComponent
import presentation.screen.request.component.request.authorization.vo.AuthVo
import theme

@Composable
fun RequestAuthorizationComponent(authVo: AuthVo) {
    Row {
        DropdownComponent(
            modifier = Modifier.width(150.dp),
            options = authVo.options,
            optionSelected = authVo.optionSelected.value,
            onOptionSelected = { authVo.onOptionSelected(it) }
        )

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

        AuthorizationType.OAUTH2 -> ComingSoonMessage()
        AuthorizationType.NONE -> {}
    }
}

@Composable
fun ComingSoonMessage(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize().border(1.dp, color = theme.colors.table.border),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Coming soon",
            color = theme.colors.input.label,
            fontSize = 20.sp
        )
    }
}