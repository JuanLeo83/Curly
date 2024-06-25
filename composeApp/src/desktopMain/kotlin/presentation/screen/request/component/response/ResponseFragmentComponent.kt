package presentation.screen.request.component.response

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.model.BodyType
import presentation.screen.request.ResponseData
import presentation.screen.request.ResponseViewMode
import presentation.screen.request.component.response.markuptext.MarkupTextComponent

@Composable
fun ResponseFragmentComponent(
    response: ResponseData,
    viewMode: ResponseViewMode,
    showPretty: () -> Unit,
    showRaw: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Row {
                Button(onClick = { showPretty() }) {
                    Text(text = "Pretty")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { showRaw() }) {
                    Text(text = "Raw")
                }
            }
            ResponseStatsComponent(
                statusCode = response.statusCode,
                responseTime = response.responseTime,
                size = response.size
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        showResponseBody(
            response = response,
            viewMode = viewMode,
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
    }
}

@Composable
private fun showResponseBody(
    response: ResponseData,
    viewMode: ResponseViewMode,
    modifier: Modifier = Modifier
) {
    if (viewMode == ResponseViewMode.PRETTY) {
        when (response.type) {
            BodyType.JSON -> JsonText(jsonString = response.body, modifier = modifier)
            BodyType.HTML,
            BodyType.XML -> MarkupTextComponent(xmlString = response.body, modifier = modifier)

            BodyType.TEXT -> Text(text = response.body, modifier = modifier)
        }
    } else Text(text = response.rawBody, modifier = modifier)
}