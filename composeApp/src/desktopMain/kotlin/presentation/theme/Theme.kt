package presentation.theme

import androidx.compose.ui.graphics.Color

data class Theme(
    val isLight: Boolean = true,
    val colors: ThemeColor = ThemeColor()
)

data class ThemeColor(
    val primary: Color = Color.Transparent,
    val secondary: Color = Color.Transparent,
    val background: Color = Color.Transparent,
    val button: Button = Button(),
    val input: InputColor = InputColor(),
    val tab: TabColor = TabColor(),
    val checkbox: Checkbox = Checkbox(),
    val requestTypes: RequestTypesColor = RequestTypesColor(),
    val syntax: SyntaxColor = SyntaxColor(),
    val response: ResponseColor = ResponseColor(),
    val body: BodyColor = BodyColor(),
    val table: TableColor = TableColor()
)

data class Button(
    val primary: ButtonColor = ButtonColor(),
    val secondary: ButtonColor = ButtonColor()
)

data class ButtonColor(
    val text: Color = Color.Transparent,
    val background: Color = Color.Transparent
)

data class InputColor(
    val border: Color = Color.Transparent,
    val background: Color = Color.Transparent,
    val text: Color = Color.Transparent,
    val placeholder: Color = Color.Transparent
)

data class TabColor(
    val background: Color = Color.Transparent,
    val text: Color = Color.Transparent,
    val active: TabActiveColor = TabActiveColor()
)

data class TabActiveColor(
    val background: Color = Color.Transparent,
    val text: Color = Color.Transparent
)

data class Checkbox(
    val enabled: CheckboxColor = CheckboxColor(),
    val disabled: CheckboxColor = CheckboxColor()
)

data class CheckboxColor(
    val border: Color = Color.Transparent,
    val background: Color = Color.Transparent,
    val checked: CheckboxCheckedColor = CheckboxCheckedColor()
)

data class CheckboxCheckedColor(
    val border: Color = Color.Transparent,
    val background: Color = Color.Transparent,
    val icon: Color = Color.Transparent
)

data class ThemeTextColor(
    val primary: Color = Color.Transparent,
    val secondary: Color = Color.Transparent,
    val highlight: Color = Color.Transparent,
    val placeholder: Color = Color.Transparent,
    val button: Color = Color.Transparent
)

data class RequestTypesColor(
    val getRequest: Color = Color.Transparent,
    val postRequest: Color = Color.Transparent,
    val putRequest: Color = Color.Transparent,
    val patchRequest: Color = Color.Transparent,
    val deleteRequest: Color = Color.Transparent,
    val headRequest: Color = Color.Transparent,
    val optionsRequest: Color = Color.Transparent
)

data class SyntaxColor(
    val markup: MarkupColor = MarkupColor(),
    val json: JsonColor = JsonColor()
)

data class MarkupColor(
    val tag: Color = Color.Transparent,
    val tagName: Color = Color.Transparent,
    val attributeName: Color = Color.Transparent,
    val attributeValue: Color = Color.Transparent,
    val content: Color = Color.Transparent
)

data class JsonColor(
    val curlyBraces: Color = Color.Transparent,
    val brackets: Color = Color.Transparent,
    val key: Color = Color.Transparent,
    val stringValue: Color = Color.Transparent,
    val numberValue: Color = Color.Transparent,
    val booleanValue: Color = Color.Transparent,
    val nullValue: Color = Color.Transparent
)

data class ResponseColor(
    val attributes: Color = Color.Transparent,
    val error: Color = Color.Transparent
)

data class BodyColor(
    val lineCounter: LineCounterColor = LineCounterColor(),
    val background: Color = Color.Transparent,
    val rawText: Color = Color.Transparent
)

data class LineCounterColor(
    val background: Color = Color.Transparent,
    val text: Color = Color.Transparent
)

data class TableColor(
    val header: TableHeaderColor = TableHeaderColor(),
    val row: TableRowColor = TableRowColor(),
    val icon: Color = Color.Transparent,
    val border: Color = Color.Transparent
)

data class TableHeaderColor(
    val background: Color = Color.Transparent,
    val text: Color = Color.Transparent
)

data class TableRowColor(
    val background: Color = Color.Transparent,
    val text: Color = Color.Transparent,
    val placeholder: Color = Color.Transparent
)