package presentation.screen.request.component.request.authorization

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.screen.request.component.request.authorization.vo.BasicAuthVo

@Composable
fun BasicAuthComponent(
    modifier: Modifier = Modifier,
    vo: BasicAuthVo
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = vo.userName,
            onValueChange = vo.onUserNameChange,
            label = { Text("Username") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = vo.password,
            onValueChange = vo.onPasswordChange,
            label = { Text("Password") }
        )
    }
}