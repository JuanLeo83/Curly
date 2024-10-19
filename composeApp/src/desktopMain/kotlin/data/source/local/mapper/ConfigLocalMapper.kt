package data.source.local.mapper

import data.source.local.entity.BodyColorEntity
import data.source.local.entity.ButtonColorEntity
import data.source.local.entity.ButtonEntity
import data.source.local.entity.CheckboxCheckedColorEntity
import data.source.local.entity.CheckboxColorEntity
import data.source.local.entity.CheckboxEntity
import data.source.local.entity.InputColorEntity
import data.source.local.entity.JsonColorEntity
import data.source.local.entity.LineCounterColorEntity
import data.source.local.entity.MarkupColorEntity
import data.source.local.entity.RequestTypesColorEntity
import data.source.local.entity.ResponseColorEntity
import data.source.local.entity.SyntaxColorEntity
import data.source.local.entity.TabActiveColorEntity
import data.source.local.entity.TabColorEntity
import data.source.local.entity.TableColorEntity
import data.source.local.entity.TableHeaderColorEntity
import data.source.local.entity.TableRowColorEntity
import data.source.local.entity.ThemeColorEntity
import data.source.local.entity.ThemeEntity
import domain.model.BodyColorModel
import domain.model.ButtonColorModel
import domain.model.ButtonModel
import domain.model.CheckboxCheckedColorModel
import domain.model.CheckboxColorModel
import domain.model.CheckboxModel
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
import domain.model.ThemesModel

class ConfigLocalMapper {

    fun mapToModel(entity: ThemeEntity) = ThemeModel(
        isLight = entity.isLight,
        colors = mapToThemeColorModel(entity.colors)
    )

    fun mapToThemesModel(currentTheme: String, themes: List<String>) = ThemesModel(
        currentTheme = currentTheme,
        allThemes = themes
    )

    private fun mapToThemeColorModel(entity: ThemeColorEntity) = ThemeColorModel(
        primary = entity.primary,
        secondary = entity.secondary,
        background = entity.background,
        button = mapToButtonModel(entity.button),
        input = mapToInputColorModel(entity.input),
        tab = mapToTabColorModel(entity.tab),
        checkbox = mapToCheckboxModel(entity.checkbox),
        requestTypes = mapToRequestTypesColorModel(entity.requestTypes),
        syntax = mapToSyntaxColorModel(entity.syntax),
        response = mapToResponseColorModel(entity.response),
        body = mapToBodyColorModel(entity.body),
        table = mapToTableColorModel(entity.table)
    )

    private fun mapToButtonModel(entity: ButtonEntity) = ButtonModel(
        primary = mapToButtonColorModel(entity.primary),
        secondary = mapToButtonColorModel(entity.secondary)
    )

    private fun mapToButtonColorModel(entity: ButtonColorEntity) = ButtonColorModel(
        text = entity.text,
        background = entity.background
    )

    private fun mapToInputColorModel(entity: InputColorEntity) = InputColorModel(
        border = entity.border,
        background = entity.background,
        text = entity.text,
        placeholder = entity.placeholder
    )

    private fun mapToTabColorModel(entity: TabColorEntity) = TabColorModel(
        background = entity.background,
        text = entity.text,
        active = mapToTabActiveColorModel(entity.active)
    )

    private fun mapToTabActiveColorModel(entity: TabActiveColorEntity) = TabActiveColorModel(
        background = entity.background,
        text = entity.text
    )

    private fun mapToCheckboxModel(entity: CheckboxEntity) = CheckboxModel(
        enabled = mapToCheckboxColorModel(entity.enabled),
        disabled = mapToCheckboxColorModel(entity.disabled)
    )

    private fun mapToCheckboxColorModel(entity: CheckboxColorEntity) = CheckboxColorModel(
        border = entity.border,
        background = entity.background,
        checked = mapToCheckboxCheckedColorModel(entity.checked)
    )

    private fun mapToCheckboxCheckedColorModel(entity: CheckboxCheckedColorEntity) = CheckboxCheckedColorModel(
        border = entity.border,
        background = entity.background,
        icon = entity.icon
    )

    private fun mapToRequestTypesColorModel(entity: RequestTypesColorEntity) = RequestTypesColorModel(
        getRequest = entity.getRequest,
        postRequest = entity.postRequest,
        putRequest = entity.putRequest,
        patchRequest = entity.patchRequest,
        deleteRequest = entity.deleteRequest,
        headRequest = entity.headRequest,
        optionsRequest = entity.optionsRequest
    )

    private fun mapToSyntaxColorModel(entity: SyntaxColorEntity) = SyntaxColorModel(
        markup = mapToMarkupColorModel(entity.markup),
        json = mapToJsonColorModel(entity.json)
    )

    private fun mapToMarkupColorModel(entity: MarkupColorEntity) = MarkupColorModel(
        tag = entity.tag,
        tagName = entity.tagName,
        attributeName = entity.attributeName,
        attributeValue = entity.attributeValue,
        content = entity.content
    )

    private fun mapToJsonColorModel(entity: JsonColorEntity) = JsonColorModel(
        curlyBraces = entity.curlyBraces,
        brackets = entity.brackets,
        key = entity.key,
        stringValue = entity.stringValue,
        numberValue = entity.numberValue,
        booleanValue = entity.booleanValue,
        nullValue = entity.nullValue
    )

    private fun mapToResponseColorModel(entity: ResponseColorEntity) = ResponseColorModel(
        attributes = entity.attributes,
        error = entity.error
    )

    private fun mapToBodyColorModel(entity: BodyColorEntity) = BodyColorModel(
        lineCounter = mapToLineCounterColorModel(entity.lineCounter),
        background = entity.background,
        rawText = entity.rawText
    )

    private fun mapToLineCounterColorModel(entity: LineCounterColorEntity): LineCounterColorModel {
        return LineCounterColorModel(
            background = entity.background,
            text = entity.text
        )
    }

    private fun mapToTableColorModel(entity: TableColorEntity) = TableColorModel(
        header = mapToTableHeaderColorModel(entity.header),
        row = mapToTableRowColorModel(entity.row),
        icon = entity.icon,
        border = entity.border
    )

    private fun mapToTableHeaderColorModel(entity: TableHeaderColorEntity) = TableHeaderColorModel(
        background = entity.background,
        text = entity.text
    )

    private fun mapToTableRowColorModel(entity: TableRowColorEntity) = TableRowColorModel(
        background = entity.background,
        text = entity.text,
        placeholder = entity.placeholder
    )

}