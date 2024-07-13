package presentation.screen.request.component.response

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.BodyType
import presentation.screen.request.ResponseData
import presentation.screen.request.ResponseViewMode
import presentation.screen.request.component.response.json.JsonText
import presentation.screen.request.component.response.markuptext.MarkupTextComponent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ResponseBodyComponent(
    response: ResponseData,
    viewMode: ResponseViewMode,
    modifier: Modifier = Modifier,
    showPretty: () -> Unit,
    showRaw: () -> Unit
) {
    var hover by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize()
        .onPointerEvent(PointerEventType.Enter) { hover = true }
        .onPointerEvent(PointerEventType.Exit) { hover = false }
    ) {
        if (viewMode == ResponseViewMode.PRETTY) {
            when (response.type) {
                BodyType.TEXT -> {
                    SelectionContainer {
                        Text(text = response.body, modifier = modifier, fontSize = 12.sp)
                    }
                }
                BodyType.JSON -> JsonText(jsonString = response.body, modifier = modifier)
                BodyType.HTML,
                BodyType.XML -> MarkupTextComponent(xmlString = response.body, modifier = modifier)
            }
        } else SelectionContainer {
            Text(text = response.rawBody, modifier = modifier, fontSize = 12.sp)
        }

        if (hover) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { showPretty() }) { Text(text = "Pretty", fontSize = 12.sp) }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { showRaw() }) { Text(text = "Raw", fontSize = 12.sp) }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}