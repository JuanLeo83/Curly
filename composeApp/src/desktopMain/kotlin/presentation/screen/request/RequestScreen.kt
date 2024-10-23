package presentation.screen.request

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import presentation.screen.request.component.request.RequestFragmentComponent
import presentation.screen.request.component.response.ResponseFragmentComponent
import theme

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun RequestScreen(
    viewModel: RequestViewModel = koinViewModel()

) {
    val state by viewModel.state.collectAsState()

    val density = LocalDensity.current
    var hover by remember { mutableStateOf(false) }
    var isDragging by remember { mutableStateOf(false) }
    var height by remember { mutableStateOf(DEFAULT_HEIGHT) }

    Box(
        modifier = Modifier.fillMaxSize().background(color = theme.colors.background).padding(16.dp)
    ) {
        Column {
            RequestFragmentComponent(
                modifier = Modifier.height(height),
                urlVo = state.urlVo.copy(
                    setRequestMethod = { viewModel.setRequestMethod(it) },
                    setUrl = { viewModel.setUrl(it) },
                    sendRequest = { viewModel.sendRequest() }
                ),
                paramsVo = state.paramsVo.copy(
                    addRow = { viewModel.addRow(TableType.PARAMS) },
                    onValueChange = { type, param -> viewModel.onValueChange(type, param) },
                    deleteRow = { type, index -> viewModel.deleteRow(type, index) }
                ),
                headersVo = state.headersVo.copy(
                    addRow = { viewModel.addRow(TableType.HEADERS) },
                    onValueChange = { type, param -> viewModel.onValueChange(type, param) },
                    deleteRow = { type, index -> viewModel.deleteRow(type, index) }
                ),
                authVo = state.authVo.copy(
                    onOptionSelected = { viewModel.setAuthorizationType(it) },
                    basic = state.authVo.basic.copy(
                        onUserNameChange = { viewModel.onBasicAuthUserNameChange(it) },
                        onPasswordChange = { viewModel.onBasicAuthPasswordChange(it) }
                    ),
                    bearer = state.authVo.bearer.copy(
                        onTokenChange = { viewModel.onBearerTokenChange(it) }
                    ),
                    apiKey = state.authVo.apiKey.copy(
                        onOptionSelected = { viewModel.onApiKeyAddToSelected(it) },
                        onKeyChange = { viewModel.onApiKeyChange(it) },
                        onValueChange = { viewModel.onApiKeyValueChange(it) }
                    )
                ),
                bodyVo = state.bodyVo.copy(
                    onOptionSelected = { viewModel.setRequestBodyType(it) },
                    setBody = { viewModel.setRequestBody(it) }
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .onPointerEvent(PointerEventType.Enter) { hover = true }
                .onPointerEvent(PointerEventType.Exit) { hover = false }
                .pointerHoverIcon(icon = PointerIcon.Hand)
                .pointerInput(Unit) {
                    detectDragGestures(
                        matcher = PointerMatcher.Primary,
                        onDragStart = { isDragging = true },
                        onDragEnd = { isDragging = false }
                    ) {
                        if (isDragging) {

                            val newHeight = with(density) {
                                (height + it.y.toDp()).coerceIn(MIN_HEIGHT, MAX_HEIGHT)
                            }

                            height = newHeight
                        }

                        isDragging = isDragging(height, it.y, hover)
                    }
                }
                .padding(vertical = 4.dp)
            ) {
                Divider(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(if (hover || isDragging) theme.colors.secondary else theme.colors.input.border)
                )

                if (state.isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth().height(1.dp),
                        color = theme.colors.secondary
                    )
                }
            }

            state.responseData?.let { response ->
                ResponseFragmentComponent(
                    response = response,
                    viewMode = state.responseViewMode,
                    showPretty = { viewModel.showPretty() },
                    showRaw = { viewModel.showRaw() }
                )
            }
        }
    }
}

fun isDragging(height: Dp, offset: Float, hover: Boolean): Boolean {
    return (height != MIN_HEIGHT && height != MAX_HEIGHT) ||
            (height == MIN_HEIGHT && offset > 0 && hover) ||
            (height == MAX_HEIGHT && offset < 0 && hover)

}

private val DEFAULT_HEIGHT = 250.dp
private val MIN_HEIGHT = 190.dp
private val MAX_HEIGHT = 500.dp

// https://pokeapi.co/api/v2/ability/?limit=50&offset=50