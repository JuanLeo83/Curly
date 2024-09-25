package presentation.screen.request.component.request.authorization

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import presentation.screen.request.component.request.authorization.model.BearerAuthVo

@Composable
fun BearerAuthComponent(
    modifier: Modifier = Modifier,
    vo: BearerAuthVo
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = vo.token,
            onValueChange = vo.onTokenChange,
            label = { Text("Token") }
        )
    }
}