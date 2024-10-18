package domain.model

data class ThemeModel(
    val colors: ThemeColorModel
)

data class ThemeColorModel(
    val primary: String,
    val secondary: String,
    val background: String,
    val button: ButtonModel,
    val input: InputColorModel,
    val tab: TabColorModel,
    val checkbox: CheckboxColorModel,
    val requestTypes: RequestTypesColorModel,
    val syntax: SyntaxColorModel,
    val response: ResponseColorModel,
    val body: BodyColorModel,
    val table: TableColorModel
)

data class ButtonModel(
    val primary: ButtonColorModel,
    val secondary: ButtonColorModel
)

data class ButtonColorModel(
    val text: String,
    val background: String
)

data class InputColorModel(
    val border: String,
    val background: String,
    val text: String,
    val placeholder: String
)

data class TabColorModel(
    val background: String,
    val text: String,
    val active: TabActiveColorModel
)

data class TabActiveColorModel(
    val background: String,
    val text: String
)

data class CheckboxColorModel(
    val border: String,
    val background: String,
    val checked: CheckboxCheckedColorModel
)

data class CheckboxCheckedColorModel(
    val border: String,
    val background: String,
    val icon: String
)

data class RequestTypesColorModel(
    val getRequest: String,
    val postRequest: String,
    val putRequest: String,
    val patchRequest: String,
    val deleteRequest: String,
    val headRequest: String,
    val optionsRequest: String
)

data class SyntaxColorModel(
    val markup: MarkupColorModel,
    val json: JsonColorModel
)

data class MarkupColorModel(
    val tag: String,
    val tagName: String,
    val attributeName: String,
    val attributeValue: String,
    val content: String
)

data class JsonColorModel(
    val curlyBraces: String,
    val brackets: String,
    val key: String,
    val stringValue: String,
    val numberValue: String,
    val booleanValue: String,
    val nullValue: String
)

data class ResponseColorModel(
    val attributes: String,
    val error: String
)

data class BodyColorModel(
    val lineCounter: LineCounterColorModel,
    val background: String,
    val rawText: String
)

data class LineCounterColorModel(
    val background: String,
    val text: String
)

data class TableColorModel(
    val header: TableHeaderColorModel,
    val row: TableRowColorModel,
    val icon: String,
    val border: String
)

data class TableHeaderColorModel(
    val background: String,
    val text: String
)

data class TableRowColorModel(
    val background: String,
    val text: String,
    val placeholder: String
)