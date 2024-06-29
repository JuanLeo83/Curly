package presentation.screen.request

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.model.RequestMethod
import domain.model.RequestResult
import domain.usecase.DoRequestUseCase
import extension.list.add
import extension.list.modify
import extension.list.remove
import kotlinx.coroutines.launch

class RequestScreenModel(
    private val doRequestUseCase: DoRequestUseCase,
    private val mapper: RequestScreenMapper
) : StateScreenModel<RequestScreenState>(RequestScreenState()) {

    fun setUrl(url: String) {
        mutableState.value = state.value.copy(url = url)
    }

    fun setRequestMethod(method: RequestMethod) {
        mutableState.value = state.value.copy(method = method)
    }

    fun sendRequest() = screenModelScope.launch {
        if (state.value.url.isEmpty()) return@launch

        val params = mapper.mapToRequestParams(state.value)
        mutableState.value = state.value.copy(url = params.url, isLoading = true)
        doRequestUseCase(params).fold(
            onSuccess = ::requestSuccessHandler,
            onFailure = ::requestFailureHandler
        )
    }

    fun showPretty() {
        mutableState.value = state.value.copy(
            responseViewMode = ResponseViewMode.PRETTY
        )
    }

    fun showRaw() {
        mutableState.value = state.value.copy(
            responseViewMode = ResponseViewMode.RAW
        )
    }

    fun addRow(type: TableType) {
        when (type) {
            TableType.PARAMS -> addRequestParam()
            TableType.HEADERS -> addRequestHeader()
            TableType.COOKIES -> Unit
            TableType.BODY -> Unit
        }
    }

    fun onValueChange(type: TableType, param: RequestParam) {
        when (type) {
            TableType.PARAMS -> modifyRequestParam(param)
            TableType.HEADERS -> modifyRequestHeader(param)
            TableType.COOKIES -> Unit
            TableType.BODY -> Unit
        }


//        val updatedParams = state.value.requestParams.map {
//            if (it.index == param.index) it.copy(
//                key = param.key,
//                value = param.value,
//                isChecked = param.isChecked
//            ) else it
//        }.toMutableList()
//        mutableState.value = state.value
//            .copy(requestParams = updatedParams)
    }

    fun deleteRow(type: TableType, index: Int) {
        when (type) {
            TableType.PARAMS -> deleteRequestParam(index)
            TableType.HEADERS -> deleteRequestHeader(index)
            TableType.COOKIES -> Unit
            TableType.BODY -> Unit
        }
    }

    private fun requestSuccessHandler(result: RequestResult) {
        mutableState.value = state.value.copy(
            isLoading = false,
            responseData = mapper.mapToResponseData(result),
            errorMessage = ""
        )
    }

    private fun requestFailureHandler(throwable: Throwable) {
        mutableState.value = state.value.copy(
            isLoading = false,
            responseData = null,
            errorMessage = throwable.message ?: ""
        )
    }

    private fun addRequestParam() {
        mutableState.value = state.value
            .copy(requestParams = state.value.requestParams
                .add(RequestParam(index = state.value.requestParams.size))
            )
    }

    private fun addRequestHeader() {
        mutableState.value = state.value
            .copy(headerParams = state.value.headerParams
                .add(RequestParam(index = state.value.headerParams.size))
            )
    }

    private fun modifyRequestParam(param: RequestParam) {
        mutableState.value = state.value.copy(
            requestParams = state.value.requestParams.modify(param, param.index)
        )


//        val updatedParams = state.value.requestParams.map {
//            if (it.index == param.index) it.copy(
//                key = param.key,
//                value = param.value,
//                isChecked = param.isChecked
//            ) else it
//        }.toMutableList()
//        mutableState.value = state.value.copy(requestParams = updatedParams)
    }

    private fun modifyRequestHeader(param: RequestParam) {
        mutableState.value = state.value.copy(
            headerParams = state.value.headerParams.modify(param, param.index)
        )
//        val updatedParams = state.value.headerParams.map {
//            if (it.index == param.index) it.copy(
//                key = param.key,
//                value = param.value,
//                isChecked = param.isChecked
//            ) else it
//        }.toMutableList()
//        mutableState.value = state.value.copy(headerParams = updatedParams)
    }

    private fun deleteRequestParam(index: Int) {
        mutableState.value = state.value.copy(
            requestParams = state.value.requestParams.remove(index)
        )
    }

    private fun deleteRequestHeader(index: Int) {
        mutableState.value = state.value.copy(
            headerParams = state.value.headerParams.remove(index)
        )
    }

}