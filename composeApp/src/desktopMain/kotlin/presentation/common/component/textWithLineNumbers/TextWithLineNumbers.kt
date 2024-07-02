package presentation.common.component.textWithLineNumbers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import presentation.common.component.lineNumbers.LineNumbersComponent

@Composable
fun TextWithLineNumbers(annotatedString: AnnotatedString, modifier: Modifier = Modifier) {
    Row {
        LineNumbersComponent(annotatedString.toString(), modifier = modifier.weight(0.05f))

        Divider(modifier = Modifier.width(1.dp).fillMaxHeight().background(Color.LightGray))

        Text(
            text = annotatedString,
            modifier = modifier.then(Modifier.weight(0.95f))
        )
    }
}