package presentation.screen.request.component.request.body

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import domain.model.BodyType
import presentation.common.component.input.getCursorBrush
import theme

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
                    modifier = modifier.fillMaxHeight().padding(top = 2.dp),
                    value = value,
                    onValueChange = { setBody(it) },
                    textStyle = TextStyle(
                        fontSize = theme.fonts.body,
                        color = theme.colors.input.text
                    ),
                    cursorBrush = getCursorBrush()
                )
            }
        }

        BodyType.JSON -> BodyJsonComponent(modifier, value, setBody)
        BodyType.XML -> BodyMarkupComponent(modifier, value, setBody)
        BodyType.HTML -> BodyMarkupComponent(modifier, value, setBody)
    }
}

@Composable
private fun FormWrapperComponent(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    content: @Composable () -> Unit
) {
    Box {
        if (value.isEmpty()) {
            Text(
                placeholder,
                fontSize = theme.fonts.input.placeholder,
                color = theme.colors.input.placeholder,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }

        Box(
            modifier = modifier
                .border(1.dp, theme.colors.input.border, RoundedCornerShape(4.dp))
                .defaultMinSize(minHeight = 100.dp)
                .padding(8.dp)
        ) {
            content()
        }
    }
}