package presentation.screen.request.component.request.authorization

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.common.component.input.InputField
import presentation.common.component.input.PasswordInputField
import presentation.common.component.input.TrailingIconVisibility
import presentation.screen.request.component.request.authorization.vo.BasicAuthVo

@Composable
fun BasicAuthComponent(
    modifier: Modifier = Modifier,
    vo: BasicAuthVo
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        InputField(
            label = "Username",
            value = vo.userName,
            onValueChange = vo.onUserNameChange,
            placeholder = "Enter a valid username",
            trailingIcon = Icons.Default.Close,
            onTrailingIconClick =  { vo.onUserNameChange("") },
            trailingIconVisibility = TrailingIconVisibility.Condition(vo.userName.isNotEmpty())
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordInputField(
            label = "Password",
            value = vo.password,
            onValueChange = vo.onPasswordChange,
            placeholder = "Enter a password",
        )
    }
}