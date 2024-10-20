package presentation.screen.request.component.response.json

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import presentation.common.component.body.JsonFormatter
import presentation.common.component.textWithLineNumbers.TextWithLineNumbers

@Composable
fun JsonText(
    jsonString: String,
    modifier: Modifier = Modifier
) {
    val jsonAnnotatedString = JsonFormatter(jsonString)

    TextWithLineNumbers(jsonAnnotatedString, modifier = modifier)
}