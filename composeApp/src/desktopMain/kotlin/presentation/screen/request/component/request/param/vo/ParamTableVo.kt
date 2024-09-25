package presentation.screen.request.component.request.param.vo

import presentation.screen.request.RequestParam
import presentation.screen.request.TableType

data class ParamTableVo(
    val tableType: TableType,
    val params: List<RequestParam> = emptyList(),
    val addRow: (TableType) -> Unit = {},
    val onValueChange: (TableType, RequestParam) -> Unit = { _, _ -> },
    val deleteRow: (TableType, index: Int) -> Unit = { _, _ -> },
)
