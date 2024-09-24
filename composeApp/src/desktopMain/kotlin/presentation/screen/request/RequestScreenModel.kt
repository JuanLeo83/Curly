package presentation.screen.request

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.model.AuthorizationType
import domain.model.BodyType
import domain.model.RequestMethod
import domain.model.ResponseModel
import domain.usecase.DoRequestUseCase
import extension.list.add
import extension.list.modify
import extension.list.remove
import extension.list.sortRequestParams
import kotlinx.coroutines.launch

class RequestScreenModel(
    private val doRequestUseCase: DoRequestUseCase,
    private val mapper: RequestScreenMapper
) : StateScreenModel<RequestScreenState>(RequestScreenState()) {

    fun setUrl(url: String) {
        mutableState.value = state.value.copy(
            url = url,
            requestParams = getRequestParams(url)
        )

        println(mutableState.value.requestParams)
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
        mutableState.value = state.value.copy(responseViewMode = ResponseViewMode.PRETTY)
    }

    fun showRaw() {
        mutableState.value = state.value.copy(responseViewMode = ResponseViewMode.RAW)
    }

    fun addRow(type: TableType) {
        when (type) {
            TableType.PARAMS -> addRequestParam()
            TableType.HEADERS -> addRequestHeader()
            TableType.COOKIES -> Unit
            TableType.BODY -> Unit
            TableType.AUTHORIZATION -> Unit
        }
    }

    fun onValueChange(type: TableType, param: RequestParam) {
        when (type) {
            TableType.PARAMS -> modifyRequestParam(param)
            TableType.HEADERS -> modifyRequestHeader(param)
            TableType.COOKIES -> Unit
            TableType.BODY -> Unit
            TableType.AUTHORIZATION -> Unit
        }
    }

    fun deleteRow(type: TableType, index: Int) {
        when (type) {
            TableType.PARAMS -> deleteRequestParam(index)
            TableType.HEADERS -> deleteRequestHeader(index)
            TableType.COOKIES -> Unit
            TableType.BODY -> Unit
            TableType.AUTHORIZATION -> Unit
        }
    }

    fun setRequestBodyType(bodyType: BodyType) {
        mutableState.value = state.value.copy(requestBodyType = bodyType)
    }

    fun setRequestBody(body: String) {
        mutableState.value = state.value.copy(requestBodyValue = body)
    }

    fun setAuthorizationType(authorizationType: AuthorizationType) {
        mutableState.value = state.value.copy(requestAuthorizationType = authorizationType)
    }

    private fun requestSuccessHandler(result: ResponseModel) {
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
            .copy(
                requestParams = state.value.requestParams
                    .add(RequestParam(index = state.value.requestParams.size))
            )
    }

    private fun addRequestHeader() {
        mutableState.value = state.value
            .copy(
                headerParams = state.value.headerParams
                    .add(RequestParam(index = state.value.headerParams.size))
            )
    }

    private fun modifyRequestParam(param: RequestParam) {
        val updatedParams = state.value.requestParams.modify(param, param.index)
        mutableState.value = state.value.copy(
            url = buildUrlWithParams(state.value.url, updatedParams),
            requestParams = updatedParams
        )
    }

    private fun modifyRequestHeader(param: RequestParam) {
        mutableState.value = state.value.copy(
            headerParams = state.value.headerParams.modify(param, param.index)
        )
    }

    private fun deleteRequestParam(index: Int) {
        val updatedParams = state.value.requestParams.remove(index)
        mutableState.value = state.value.copy(
            url = buildUrlWithParams(state.value.url, updatedParams),
            requestParams = state.value.requestParams.remove(index)
        )
    }

    private fun deleteRequestHeader(index: Int) {
        mutableState.value = state.value.copy(
            headerParams = state.value.headerParams.remove(index)
        )
    }

    private fun getRequestParams(url: String): List<RequestParam> {
        return (
                url.substringAfter("?", "")
                    .split("&")
                    .filter { it.contains("=") }
                    .mapIndexed { index, text ->
                        val (key, value) = text.split("=")
                        RequestParam(isChecked = true, index = index, key = key, value = value)
                    } + state.value.requestParams.filter { !it.isChecked }
                ).sortRequestParams()
    }

    private fun buildUrlWithParams(baseUrl: String, params: List<RequestParam>): String {
        if (baseUrl.isEmpty()) return baseUrl

        val queryParams = params
            .filter { it.isChecked }
            .joinToString("&") { "${it.key}=${it.value}" }
        return if (queryParams.isEmpty())
            baseUrl.split("?").first()
        else "${baseUrl.split("?").first()}?$queryParams"
    }

}