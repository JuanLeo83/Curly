package presentation.screen.request

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel


class RequestScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<RequestScreenModel>()
        val state = screenModel.state.collectAsState().value

        screenModel.getMessage()

        Column {
            Text(state.message)
            if (state.errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(state.errorMessage, color = Color.Red)
            }
        }
    }
}