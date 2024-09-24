package presentation.screen.request.component.request.body

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Divider
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.common.Symbols.NEW_LINE
import presentation.common.Symbols.SPACE
import presentation.common.component.lineNumbers.LineNumbersComponent
import presentation.screen.request.component.response.markuptext.MarkupElement
import presentation.screen.request.component.response.markuptext.MarkupTextUtils
import presentation.screen.request.component.response.markuptext.XmlElementType

@Composable
fun BodyMarkupComponent(
    modifier: Modifier = Modifier,
    xmlValue: String,
    setXmlValue: (String) -> Unit
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(xmlValue)) }

    val markupAnnotatedString = remember(xmlValue) {
        buildAnnotatedString {
            val rows = xmlValue.split(NEW_LINE)
            for (row in rows) {
                val elements = MarkupTextUtils.identifyElements(row)
                if (elements.isEmpty()) {
                    append(row)
                    append(NEW_LINE)
                } else {
                    append(MarkupTextUtils.getTabulation(row))
                    for (element in elements) {
                        withStyle(
                            style = SpanStyle(
                                color = getColor(element),
                                fontWeight = FontWeight.Light
                            )
                        ) {
                            append(element.value)
                        }
                    }
                    append(NEW_LINE)
                }
            }
        }
    }

    XmlFormWithLineNumbers(
        modifier = modifier,
        annotatedString = markupAnnotatedString,
        textFieldValue = textFieldValue
    ) {
        textFieldValue = it
        setXmlValue(it.text)
    }
}

@Composable
fun XmlFormWithLineNumbers(
    modifier: Modifier = Modifier,
    annotatedString: AnnotatedString,
    textFieldValue: TextFieldValue,
    setTextFieldValue: (TextFieldValue) -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .border(1.dp, Color.Gray, MaterialTheme.shapes.small)
            .defaultMinSize(minHeight = 100.dp)
            .padding(8.dp)
    ) {
        Row {
            LineNumbersComponent(annotatedString.toString(), modifier = modifier.weight(0.05f))

            Divider(modifier = Modifier.width(1.dp).fillMaxHeight().background(Color.LightGray))

            Row(modifier = Modifier.weight(0.95f)) {
                SelectionContainer {
                    BasicTextField(
                        value = textFieldValue,
                        onValueChange = { setTextFieldValue(it) },
                        modifier = modifier.horizontalScroll(scrollState).fillMaxHeight()
                            .padding(start = 4.dp),
                        textStyle = LocalTextStyle.current.copy(
                            color = Color.Transparent,
                            fontSize = 14.sp
                        )
                    ) { innerTextField ->
                        Text(
                            annotatedString,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(0.5f).padding(start = 4.dp)
                        )
                        innerTextField()
                    }
                }
            }
        }
    }
}

private fun AnnotatedString.Builder.getColor(element: MarkupElement): Color {
    return when (element.type) {
        XmlElementType.OPEN_TAG_SYMBOL -> Color.Blue
        XmlElementType.TAG_NAME -> Color.Red
        XmlElementType.ATTRIBUTE_NAME -> {
            append(SPACE)
            Color.Green
        }

        XmlElementType.EQUAL_SYMBOL -> Color.Black
        XmlElementType.ATTRIBUTE_VALUE -> Color.Yellow
        XmlElementType.CLOSE_TAG_SYMBOL -> Color.Blue
        XmlElementType.OPEN_END_TAG_SYMBOL -> Color.Blue
        XmlElementType.END_TAG_NAME -> Color.Red
        XmlElementType.TAG_CONTENT -> Color.Black
    }
}