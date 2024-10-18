package presentation.theme

import androidx.compose.ui.graphics.Color
import domain.model.BodyColorModel
import domain.model.ButtonColorModel
import domain.model.ButtonModel
import domain.model.CheckboxCheckedColorModel
import domain.model.CheckboxColorModel
import domain.model.InputColorModel
import domain.model.JsonColorModel
import domain.model.LineCounterColorModel
import domain.model.MarkupColorModel
import domain.model.RequestTypesColorModel
import domain.model.ResponseColorModel
import domain.model.SyntaxColorModel
import domain.model.TabActiveColorModel
import domain.model.TabColorModel
import domain.model.TableColorModel
import domain.model.TableHeaderColorModel
import domain.model.TableRowColorModel
import domain.model.ThemeColorModel
import domain.model.ThemeModel
import extension.toColorInt

class ThemeMapper {

    fun mapToTheme(model: ThemeModel) = Theme(
        colors = mapToThemeColors(model.colors)
    )

    private fun mapToThemeColors(model: ThemeColorModel) = ThemeColor(
        primary = Color(model.primary.toColorInt()),
        secondary = Color(model.secondary.toColorInt()),
        background = Color(model.background.toColorInt()),
        button = mapToButton(model.button),
        input = mapToInputColor(model.input),
        tab = mapToTabColor(model.tab),
        checkbox = mapToCheckboxColor(model.checkbox),
        requestTypes = mapToRequestTypesColor(model.requestTypes),
        syntax = mapToSyntaxColor(model.syntax),
        response = mapToResponseColor(model.response),
        body = mapToBodyColor(model.body),
        table = mapToTableColor(model.table)
    )

    private fun mapToButton(model: ButtonModel) = Button(
        primary = mapToButtonColor(model.primary),
        secondary = mapToButtonColor(model.secondary)
    )

    private fun mapToButtonColor(model: ButtonColorModel) = ButtonColor(
        text = Color(model.text.toColorInt()),
        background = Color(model.background.toColorInt())
    )

    private fun mapToInputColor(model: InputColorModel) = InputColor(
        border = Color(model.border.toColorInt()),
        background = Color(model.background.toColorInt()),
        text = Color(model.text.toColorInt()),
        placeholder = Color(model.placeholder.toColorInt())
    )

    private fun mapToTabColor(model: TabColorModel) = TabColor(
        background = Color(model.background.toColorInt()),
        text = Color(model.text.toColorInt()),
        active = mapToTabActiveColor(model.active)
    )

    private fun mapToTabActiveColor(model: TabActiveColorModel) = TabActiveColor(
        background = Color(model.background.toColorInt()),
        text = Color(model.text.toColorInt())
    )

    private fun mapToCheckboxColor(model: CheckboxColorModel) = CheckboxColor(
        border = Color(model.border.toColorInt()),
        background = Color(model.background.toColorInt()),
        checked = mapToCheckboxCheckedColor(model.checked)
    )

    private fun mapToCheckboxCheckedColor(model: CheckboxCheckedColorModel) = CheckboxCheckedColor(
        border = Color(model.border.toColorInt()),
        background = Color(model.background.toColorInt()),
        icon = Color(model.icon.toColorInt())
    )

    private fun mapToRequestTypesColor(model: RequestTypesColorModel) = RequestTypesColor(
        getRequest = Color(model.getRequest.toColorInt()),
        postRequest = Color(model.postRequest.toColorInt()),
        putRequest = Color(model.putRequest.toColorInt()),
        patchRequest = Color(model.patchRequest.toColorInt()),
        deleteRequest = Color(model.deleteRequest.toColorInt()),
        headRequest = Color(model.headRequest.toColorInt()),
        optionsRequest = Color(model.optionsRequest.toColorInt())
    )

    private fun mapToSyntaxColor(model: SyntaxColorModel) = SyntaxColor(
        markup = mapToMarkupColor(model.markup),
        json = mapToJsonColor(model.json)
    )

    private fun mapToMarkupColor(model: MarkupColorModel) = MarkupColor(
        tag = Color(model.tag.toColorInt()),
        tagName = Color(model.tagName.toColorInt()),
        attributeName = Color(model.attributeName.toColorInt()),
        attributeValue = Color(model.attributeValue.toColorInt()),
        content = Color(model.content.toColorInt())
    )

    private fun mapToJsonColor(model: JsonColorModel) = JsonColor(
        curlyBraces = Color(model.curlyBraces.toColorInt()),
        brackets = Color(model.brackets.toColorInt()),
        key = Color(model.key.toColorInt()),
        stringValue = Color(model.stringValue.toColorInt()),
        numberValue = Color(model.numberValue.toColorInt()),
        booleanValue = Color(model.booleanValue.toColorInt()),
        nullValue = Color(model.nullValue.toColorInt())
    )

    private fun mapToResponseColor(model: ResponseColorModel) = ResponseColor(
        attributes = Color(model.attributes.toColorInt()),
        error = Color(model.error.toColorInt()),
    )

    private fun mapToBodyColor(model: BodyColorModel) = BodyColor(
        lineCounter = mapToLineCounterColor(model.lineCounter),
        background = Color(model.background.toColorInt()),
        rawText = Color(model.rawText.toColorInt())
    )

    private fun mapToLineCounterColor(model: LineCounterColorModel) = LineCounterColor(
        background = Color(model.background.toColorInt()),
        text = Color(model.text.toColorInt())
    )

    private fun mapToTableColor(model: TableColorModel) = TableColor(
        header = mapToTableHeaderColor(model.header),
        row = mapToTableRowColor(model.row),
        icon = Color(model.icon.toColorInt()),
        border = Color(model.border.toColorInt())
    )

    private fun mapToTableHeaderColor(model: TableHeaderColorModel) = TableHeaderColor(
        background = Color(model.background.toColorInt())
    )

    private fun mapToTableRowColor(model: TableRowColorModel) = TableRowColor(
        background = Color(model.background.toColorInt())
    )
}