package data.source.local.entity

import kotlinx.serialization.Serializable

@Serializable
data class ThemeEntity(
    val colors: ThemeColorEntity
)

@Serializable
data class ThemeColorEntity(
    val primary: String,
    val secondary: String,
    val background: String,
    val button: ButtonEntity,
    val input: InputColorEntity,
    val tab: TabColorEntity,
    val checkbox: CheckboxColorEntity,
    val requestTypes: RequestTypesColorEntity,
    val syntax: SyntaxColorEntity,
    val response: ResponseColorEntity,
    val body: BodyColorEntity,
    val table: TableColorEntity
)

@Serializable
data class ButtonEntity(
    val primary: ButtonColorEntity,
    val secondary: ButtonColorEntity
)

@Serializable
data class ButtonColorEntity(
    val text: String,
    val background: String
)

@Serializable
data class InputColorEntity(
    val border: String,
    val background: String,
    val text: String,
    val placeholder: String
)

@Serializable
data class TabColorEntity(
    val background: String,
    val text: String,
    val active: TabActiveColorEntity
)

@Serializable
data class TabActiveColorEntity(
    val background: String,
    val text: String
)

@Serializable
data class CheckboxColorEntity(
    val border: String,
    val background: String,
    val checked: CheckboxCheckedColorEntity
)

@Serializable
data class CheckboxCheckedColorEntity(
    val border: String,
    val background: String,
    val icon: String
)

@Serializable
data class RequestTypesColorEntity(
    val getRequest: String,
    val postRequest: String,
    val putRequest: String,
    val patchRequest: String,
    val deleteRequest: String,
    val headRequest: String,
    val optionsRequest: String
)

@Serializable
data class SyntaxColorEntity(
    val markup: MarkupColorEntity,
    val json: JsonColorEntity
)

@Serializable
data class MarkupColorEntity(
    val tag: String,
    val tagName: String,
    val attributeName: String,
    val attributeValue: String,
    val content: String
)

@Serializable
data class JsonColorEntity(
    val curlyBraces: String,
    val brackets: String,
    val key: String,
    val stringValue: String,
    val numberValue: String,
    val booleanValue: String,
    val nullValue: String
)

@Serializable
data class ResponseColorEntity(
    val attributes: String,
    val error: String
)

@Serializable
data class BodyColorEntity(
    val lineCounter: LineCounterColorEntity,
    val background: String,
    val rawText: String
)

@Serializable
data class LineCounterColorEntity(
    val background: String,
    val text: String
)

@Serializable
data class TableColorEntity(
    val header: TableHeaderColorEntity,
    val row: TableRowColorEntity,
    val icon: String,
    val border: String
)

@Serializable
data class TableHeaderColorEntity(
    val background: String,
    val text: String
)

@Serializable
data class TableRowColorEntity(
    val background: String,
    val text: String,
    val placeholder: String
)