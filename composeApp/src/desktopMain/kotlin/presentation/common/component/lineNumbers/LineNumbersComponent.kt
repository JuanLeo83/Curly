package presentation.common.component.lineNumbers

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme

@Composable
fun LineNumbersComponent(text: String, modifier: Modifier = Modifier) {
    Text(
        text = getLineNumbers(text),
        fontSize = 12.sp,
        overflow = TextOverflow.Clip,
        textAlign = TextAlign.End,
        color = theme.colors.body.lineCounter.text,
        modifier = modifier.padding(vertical = 8.dp, horizontal = 4.dp)
    )
}

private fun getLineNumbers(text: String): String {
    val stringBuilder = StringBuilder()
    (1 .. (text.count { it == '\n' } + 1)).forEach {
        stringBuilder.append("$it\n")
    }
    return stringBuilder.toString()
}