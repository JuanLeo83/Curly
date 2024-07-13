package presentation.common.component.lineNumbers

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun LineNumbersComponent(text: String, modifier: Modifier = Modifier) {
    Text(
        text = getLineNumbers(text),
        fontSize = 12.sp,
        modifier = modifier
    )
}

private fun getLineNumbers(text: String): String {
    val stringBuilder = StringBuilder()
    (1 .. (text.count { it == '\n' } + 1)).forEach {
        stringBuilder.append("$it\n")
    }
    return stringBuilder.toString()
}