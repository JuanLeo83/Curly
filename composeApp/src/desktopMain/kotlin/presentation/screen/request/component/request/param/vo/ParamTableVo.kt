package presentation.screen.request.component.request.param.vo

import extension.list.add
import extension.list.modify
import extension.list.remove
import presentation.screen.request.RequestParam
import presentation.screen.request.TableType

data class ParamTableVo(
    val tableType: TableType,
    val params: List<RequestParam> = emptyList(),
    val addRow: (TableType) -> Unit = {},
    val onValueChange: (TableType, RequestParam) -> Unit = { _, _ -> },
    val deleteRow: (TableType, index: Int) -> Unit = { _, _ -> },
) {
    fun addParamSocket() = copy(params = params.add(RequestParam(index = params.size)))

    fun modifyParamSocket(param: RequestParam) = copy(params = params.modify(param, param.index))

    fun deleteParamSocket(index: Int) = copy(params = params.remove(index))
}
