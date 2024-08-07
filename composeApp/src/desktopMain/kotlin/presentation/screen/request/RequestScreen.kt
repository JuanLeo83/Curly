package presentation.screen.request

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import presentation.screen.request.component.request.RequestFragmentComponent
import presentation.screen.request.component.response.ResponseFragmentComponent

class RequestScreen : Screen {

    @OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<RequestScreenModel>()
        val state by screenModel.state.collectAsState()

        val density = LocalDensity.current
        var hover by remember { mutableStateOf(false) }
        var isDragging by remember { mutableStateOf(false) }
        var height by remember { mutableStateOf(DEFAULT_HEIGHT) }

        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Column {
                RequestFragmentComponent(
                    modifier = Modifier.height(height),
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
                    Divider(modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(if (hover || isDragging) Color.Blue else Color.LightGray)
                    )
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

    private fun isDragging(height: Dp, offset: Float, hover: Boolean): Boolean {
        return (height != MIN_HEIGHT && height != MAX_HEIGHT) ||
            (height == MIN_HEIGHT && offset > 0 && hover) ||
            (height == MAX_HEIGHT && offset < 0 && hover)

    }

    private companion object {
        val DEFAULT_HEIGHT = 250.dp
        val MIN_HEIGHT = 190.dp
        val MAX_HEIGHT = 500.dp
    }
}

// https://pokeapi.co/api/v2/ability/?limit=50&offset=50