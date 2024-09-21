package presentation.screen.request.component.request.body

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.BodyType

@Composable
fun BodyFormComponent(
    modifier: Modifier = Modifier,
    optionSelected: BodyType,
    value: String,
    setBody: (String) -> Unit
) {

    FormSelector(modifier, optionSelected, value, setBody)


}

@Composable
private fun FormSelector(
    modifier: Modifier,
    optionSelected: BodyType,
    value: String,
    setBody: (String) -> Unit
) {

    when (optionSelected) {
        BodyType.NONE -> Unit
        BodyType.TEXT -> {
            FormWrapperComponent(modifier, value, "Raw content here...") {
                BasicTextField(
                    modifier = modifier.fillMaxHeight(),
                    value = value,
                    onValueChange = { setBody(it) },
                    textStyle = TextStyle(fontSize = 14.sp)
                )
            }
        }

        BodyType.JSON -> Unit
        BodyType.XML -> Unit
        BodyType.HTML -> Unit
    }
}

@Composable
fun FormWrapperComponent(
    modifier: Modifier = Modifier,
    value: String,
    hintMessage: String,
    content: @Composable () -> Unit
) {
    Box {
        if (value.isEmpty()) {
            Text(
                hintMessage,
                fontSize = 14.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }

        Box(
            modifier = modifier
                .border(1.dp, Color.Gray, MaterialTheme.shapes.small)
                .defaultMinSize(minHeight = 100.dp)
                .padding(8.dp)
        ) {
            content()
        }
    }
}