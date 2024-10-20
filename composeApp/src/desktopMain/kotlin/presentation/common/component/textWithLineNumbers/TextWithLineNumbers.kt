package presentation.common.component.textWithLineNumbers

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.common.component.lineNumbers.LineNumbersComponent
import theme

@Composable
fun TextWithLineNumbers(annotatedString: AnnotatedString, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Row {
        LineNumbersComponent(annotatedString.toString(), modifier = modifier.weight(0.05f))

        Divider(modifier = Modifier.width(1.dp).fillMaxHeight().background(theme.colors.input.border))

        Column(modifier = Modifier.weight(0.95f).padding(top = 8.dp)) {
            SelectionContainer {
                Text(
                    text = annotatedString,
                    fontSize = 12.sp,
                    modifier = modifier.horizontalScroll(scrollState)
                )
            }
        }
    }
}