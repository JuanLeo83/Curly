package presentation.screen.request.component.request

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import domain.model.RequestMethod

@Composable
fun RequestFragmentComponent(
    method: RequestMethod,
    url: String,
    setRequestMethod: (RequestMethod) -> Unit,
    setUrl: (String) -> Unit,
    sendRequest: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            RequestMethodDropdownComponent(
                modifier = Modifier.weight(0.2f),
                optionSelected = method
            ) { setRequestMethod(it) }

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = url,
                onValueChange = { setUrl(it) },
                singleLine = true,
                maxLines = 1,
                modifier = Modifier.weight(0.6f)
                    .focusRequester(focusRequester)
                    .onPreviewKeyEvent { event ->
                        if (event.type == KeyEventType.KeyUp && event.key == Key.Enter) {
                            sendRequest()
                            focusManager.clearFocus(force = true)
                            true
                        } else false
                    }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { sendRequest() },
                modifier = Modifier.requiredWidth(120.dp)
            ) { Text("Send") }
        }
    }
}