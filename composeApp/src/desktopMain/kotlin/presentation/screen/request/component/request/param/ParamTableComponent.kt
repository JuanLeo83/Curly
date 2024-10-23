package presentation.screen.request.component.request.param

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import presentation.common.component.checkbox.CustomCheckbox
import presentation.common.component.input.getCursorBrush
import presentation.screen.request.RequestParam
import presentation.screen.request.component.request.param.vo.ParamTableVo
import theme

@Composable
fun ParamTableComponent(vo: ParamTableVo) {
    val column1Weight = .5f
    val column2Weight = .5f

    Column {
        LazyColumn(Modifier.fillMaxWidth()) {
            item {
                RequestParamsHeaderComponent(
                    keyColumnWeight = column1Weight,
                    valueColumnWeight = column2Weight
                )
            }

            itemsIndexed(vo.params) { index, data ->
                TableRowComponent(
                    index = index,
                    param = data,
                    keyColumnWeight = column1Weight,
                    valueColumnWeight = column2Weight,
                    onValueChange = { param -> vo.onValueChange(vo.tableType, param) },
                    deleteRow = { vo.deleteRow(vo.tableType, data.index) }
                )
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 16.dp)
                ) {
                    Button(
                        onClick = { vo.addRow(vo.tableType) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = theme.colors.button.secondary.background
                        ),
                        modifier = Modifier.height(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add icon",
                            modifier = Modifier.size(18.dp),
                            tint = theme.colors.button.secondary.text
                        )
                    }
                }
            }
        }


    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TableRowComponent(
    index: Int,
    param: RequestParam,
    keyColumnWeight: Float,
    valueColumnWeight: Float,
    onValueChange: (param: RequestParam) -> Unit = {},
    deleteRow: () -> Unit = {}
) {
    var isHover by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .onPointerEvent(PointerEventType.Enter) { isHover = true }
            .onPointerEvent(PointerEventType.Exit) { isHover = false }
            .drawBehind {
                if (index == 0) {
                    drawLine(
                        color = theme.colors.table.border,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 1.5f
                    )
                }
                drawLine(
                    color = theme.colors.table.border,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1.5f
                )
                drawLine(
                    color = theme.colors.table.border,
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = 1.5f
                )
                drawLine(
                    color = theme.colors.table.border,
                    start = Offset(size.width, 0f),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1.5f
                )
            }
            .padding(start = 15.dp)
    ) {
        CustomCheckbox(
            enabled = param.key.isNotEmpty() && param.value.isNotEmpty(),
            checked = param.isChecked,
            onCheckedChange = { onValueChange(param.copy(isChecked = !param.isChecked)) }
        )
        Spacer(modifier = Modifier.width(15.dp))
        TableCellComponent(
            modifier = Modifier.weight(keyColumnWeight),
            text = param.key,
            onValueChange = { k -> onValueChange(param.copy(key = k)) }
        )
        Divider(modifier = Modifier.border(1.dp, color = theme.colors.table.border).fillMaxHeight().width(1.dp))
        Spacer(modifier = Modifier.width(7.dp))
        TableCellComponent(
            modifier = Modifier.weight(valueColumnWeight),
            text = param.value,
            isHover = isHover,
            onValueChange = { v -> onValueChange(param.copy(value = v)) },
            onClickDelete = deleteRow
        )
    }
}

@Composable
fun TableCellComponent(
    modifier: Modifier = Modifier,
    text: String,
    isHover: Boolean = false,
    onValueChange: (String) -> Unit = {},
    onClickDelete: () -> Unit = {}
) {
    Box(contentAlignment = Alignment.CenterEnd, modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            maxLines = 1,
            textStyle = TextStyle(color = theme.colors.table.row.text),
            singleLine = true,
            cursorBrush = getCursorBrush(),
            modifier = Modifier.fillMaxWidth().padding(end = 8.dp)
        )
        if (isHover) {
            Row {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete icon",
                    modifier = Modifier.width(16.dp).clickable(onClick = onClickDelete),
                    tint = theme.colors.table.icon
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun RequestParamsHeaderComponent(
    keyColumnWeight: Float = 0.5f,
    valueColumnWeight: Float = 0.5f
) {
    Row(modifier = Modifier
        .clip(shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
        .background(color = theme.colors.table.header.background)
    ) {
        Spacer(modifier = Modifier.width(44.dp))
        TableTitleCellComponent(text = "Key", weight = keyColumnWeight)
        TableTitleCellComponent(text = "Value", weight = valueColumnWeight)
    }
}

@Composable
fun RowScope.TableTitleCellComponent(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = theme.fonts.table.header,
        fontWeight = FontWeight.Bold,
        color = theme.colors.table.header.text,
        modifier = Modifier
            .weight(weight)
            .padding(top = 2.dp, bottom = 6.dp, start = 4.dp, end = 4.dp)
    )
}