package presentation.screen.request.component.request.authorization

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.AuthorizationType
import presentation.screen.request.component.request.authorization.model.AuthVo

@Composable
fun RequestAuthorizationComponent(
    modifier: Modifier = Modifier,
    optionSelected: AuthorizationType,
    apiKeyAddToSelected: ApiKeyAddTo,
    onApiKeyAddToSelected: (ApiKeyAddTo) -> Unit,
    setAuthorizationType: (AuthorizationType) -> Unit,
    authVo: AuthVo
) {
    Row {
        AuthorizationTypeDropdownComponent(
            optionSelected = optionSelected
        ) { setAuthorizationType(it) }

        Spacer(modifier = Modifier.width(8.dp))

        AuthenticationComponentSelector(
            optionSelected = optionSelected,
            apiKeyAddToSelected = apiKeyAddToSelected,
            onApiKeyAddToSelected = onApiKeyAddToSelected,
            authVo = authVo
        )
    }
}

@Composable
private fun AuthenticationComponentSelector(
    optionSelected: AuthorizationType,
    apiKeyAddToSelected: ApiKeyAddTo,
    onApiKeyAddToSelected: (ApiKeyAddTo) -> Unit,
    authVo: AuthVo
) {
   when (optionSelected) {
       AuthorizationType.BASIC -> BasicAuthComponent(vo = authVo.basic)
       AuthorizationType.BEARER -> BearerAuthComponent(vo = authVo.bearer)
       AuthorizationType.API_KEY -> ApiKeyAuthComponent(
           optionSelected = apiKeyAddToSelected,
              onOptionSelected = onApiKeyAddToSelected
       )
       AuthorizationType.OAUTH2 -> Text("Coming soon")
       AuthorizationType.NONE -> {}
   }
}