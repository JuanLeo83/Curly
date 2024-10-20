package presentation.screen.request.component.response.markuptext

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import presentation.common.component.body.MarkupFormatter
import presentation.common.component.textWithLineNumbers.TextWithLineNumbers

@Composable
fun MarkupTextComponent(
    markupString: String,
    modifier: Modifier = Modifier
) {
    val markupAnnotatedString = MarkupFormatter(markupString)

    TextWithLineNumbers(markupAnnotatedString, modifier = modifier)
}