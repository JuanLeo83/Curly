package presentation.screen.request.component.request.authorization

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import presentation.common.component.input.InputField
import presentation.screen.request.component.request.authorization.vo.BearerAuthVo

@Composable
fun BearerAuthComponent(
    modifier: Modifier = Modifier,
    vo: BearerAuthVo
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        InputField(
            label = "Token",
            value = vo.token,
            onValueChange = vo.onTokenChange,
            placeholder = "Enter token"
        )
    }
}