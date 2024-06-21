package presentation.screen.request

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import presentation.screen.request.component.RequestMethodDropdownComponent

class RequestScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<RequestScreenModel>()
        val state = screenModel.state.collectAsState().value

        val scrollState = rememberScrollState()

        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RequestMethodDropdownComponent(
                        modifier = Modifier.weight(0.2f),
                        optionSelected = state.method
                    ) { screenModel.setRequestMethod(it) }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = state.url,
                        onValueChange = { screenModel.setUrl(it) },
                        singleLine = true,
                        maxLines = 1,
                        modifier = Modifier.weight(0.6f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { screenModel.sendRequest() },
                        modifier = Modifier.requiredWidth(120.dp)
                    ) { Text("Send") }
                }

                Spacer(modifier = Modifier.height(16.dp))

                state.responseData?.let { response ->
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Public,
                            contentDescription = "Network icon",
                            modifier = Modifier.width(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Status: ")
                        Text(text = response.statusCode)
                        Spacer(modifier = Modifier.width(16.dp))

                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = "Timer icon",
                            modifier = Modifier.width(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Time: ")
                        Text(text = response.responseTime)
                        Spacer(modifier = Modifier.width(16.dp))

                        Icon(
                            imageVector = Icons.Default.FileDownload,
                            contentDescription = "Size icon",
                            modifier = Modifier.width(24.dp)
                        )
                        Text(text = "Size: ")
                        Text(text = response.size)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = response.body,
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(8.dp)
                            .verticalScroll(scrollState)
                    )
                    VerticalScrollbar(
                        adapter = rememberScrollbarAdapter(scrollState)
                    )
                }
            }
        }
    }
}

// https://pokeapi.co/api/v2/ability/?limit=50&offset=50