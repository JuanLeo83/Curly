package presentation.screen.request

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import presentation.screen.request.component.request.RequestFragmentComponent
import presentation.screen.request.component.response.ResponseFragmentComponent

class RequestScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<RequestScreenModel>()
        val state by screenModel.state.collectAsState()

        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Column {
                RequestFragmentComponent(
                    method = state.method,
                    url = state.url,
                    requestParams = state.requestParams,
                    headerParams = state.headerParams,
                    setRequestMethod = { screenModel.setRequestMethod(it) },
                    setUrl = { screenModel.setUrl(it) },
                    addRow = { screenModel.addRow(it) },
                    onValueChange = { type, param -> screenModel.onValueChange(type, param) },
                    deleteRow = { type, index -> screenModel.deleteRow(type, index) },
                    sendRequest = { screenModel.sendRequest() }
                )

                Spacer(modifier = Modifier.height(if (state.isLoading) 14.dp else 16.dp))

                if (state.isLoading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth().height(2.dp))
                }

                state.responseData?.let { response ->
                    ResponseFragmentComponent(
                        response = response,
                        viewMode = state.responseViewMode,
                        showPretty = { screenModel.showPretty() },
                        showRaw = { screenModel.showRaw() }
                    )
                }
            }
        }
    }
}

// https://pokeapi.co/api/v2/ability/?limit=50&offset=50