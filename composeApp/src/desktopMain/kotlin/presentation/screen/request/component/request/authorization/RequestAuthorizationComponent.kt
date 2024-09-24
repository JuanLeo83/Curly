package presentation.screen.request.component.request.authorization

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.AuthorizationType

@Composable
fun RequestAuthorizationComponent(
    modifier: Modifier = Modifier,
    optionSelected: AuthorizationType,
    setAuthorizationType: (AuthorizationType) -> Unit
) {
    Row {
        AuthorizationTypeDropdownComponent(
            optionSelected = optionSelected
        ) { setAuthorizationType(it) }

        Spacer(modifier = Modifier.width(8.dp))
    }
}