package presentation.screen.request

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.RequestMethod
import domain.model.ResponseModel
import domain.usecase.DoRequestUseCase
import extension.list.modify
import extension.list.remove
import extension.list.sortRequestParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RequestViewModel(
    private val doRequestUseCase: DoRequestUseCase,
    private val mapper: RequestScreenMapper
) : ViewModel() {
    
    private val _state: MutableStateFlow<RequestScreenState> = MutableStateFlow(RequestScreenState())
    val state: StateFlow<RequestScreenState> = _state.asStateFlow()

    fun setUrl(url: String) {
        _state.value = state.value.copy(
            urlVo = state.value.urlVo.copy(url = url),
            paramsVo = state.value.paramsVo.copy(params = getRequestParams(url))
        )
    }

    fun setRequestMethod(method: RequestMethod) {
        _state.value = state.value.setMethod(method)
    }

    fun sendRequest() = viewModelScope.launch {
        if (state.value.urlVo.url.isEmpty()) return@launch

        val params = mapper.mapToRequestParams(state.value)
        _state.value = state.value.copy(
            urlVo = state.value.urlVo.copy(url = params.url),
            isLoading = true
        )
        doRequestUseCase(params).fold(
            onSuccess = ::requestSuccessHandler,
            onFailure = ::requestFailureHandler
        )
    }

    fun showPretty() {
        _state.value = state.value.copy(responseViewMode = ResponseViewMode.PRETTY)
    }

    fun showRaw() {
        _state.value = state.value.copy(responseViewMode = ResponseViewMode.RAW)
    }

    fun addRow(type: TableType) {
        when (type) {
            TableType.PARAMS -> addRequestParam()
            TableType.HEADERS -> addRequestHeader()
            else -> Unit
        }
    }

    fun onValueChange(type: TableType, param: RequestParam) {
        when (type) {
            TableType.PARAMS -> modifyRequestParam(param)
            TableType.HEADERS -> modifyRequestHeader(param)
            else -> Unit
        }
    }

    fun deleteRow(type: TableType, index: Int) {
        when (type) {
            TableType.PARAMS -> deleteRequestParam(index)
            TableType.HEADERS -> deleteRequestHeader(index)
            else -> Unit
        }
    }

    fun setRequestBodyType(optionSelected: String) {
        val bodyType = mapper.mapBodyTypeByName(optionSelected)
        _state.value = state.value.setBodyType(bodyType)
    }

    fun setRequestBody(value: String) {
        _state.value = state.value.setBody(value)
    }

    fun setAuthorizationType(authorizationType: String) {
        val authType = mapper.mapAuthorizationTypeByName(authorizationType)
        _state.value = state.value.setAuthorizationType(authType)
    }

    fun onBasicAuthUserNameChange(userName: String) {
        _state.value = state.value.setBasicUserName(userName)
    }

    fun onBasicAuthPasswordChange(password: String) {
        _state.value = state.value.setBasicPassword(password)
    }

    fun onBearerTokenChange(token: String) {
        _state.value = state.value.setBearerToken(token)
    }

    fun onApiKeyAddToSelected(optionSelected: String) {
        val addTo = mapper.mapAddToByName(optionSelected)
        _state.value = state.value.setApiKeyAddTo(addTo)
    }

    fun onApiKeyChange(apiKey: String) {
        _state.value = state.value.setApiKey(apiKey)
    }

    fun onApiKeyValueChange(apiKeyValue: String) {
        _state.value = state.value.setApiKeyValue(apiKeyValue)
    }

    private fun requestSuccessHandler(result: ResponseModel) {
        _state.value = state.value.copy(
            isLoading = false,
            responseData = mapper.mapToResponseData(result),
            errorMessage = ""
        )
    }

    private fun requestFailureHandler(throwable: Throwable) {
        _state.value = state.value.copy(
            isLoading = false,
            responseData = null,
            errorMessage = throwable.message ?: ""
        )
    }

    private fun addRequestParam() {
        _state.value = state.value.addParamSocket()
    }

    private fun addRequestHeader() {
        _state.value = state.value.addHeaderSocket()
    }

    private fun modifyRequestParam(param: RequestParam) {
        val updatedParams = state.value.paramsVo.params.modify(param, param.index)
        _state.value = state.value.copy(
            urlVo = state.value.urlVo.copy(
                url = buildUrlWithParams(state.value.urlVo.url, updatedParams)
            ),
            paramsVo = state.value.paramsVo.copy(params = updatedParams)
        )
    }

    private fun modifyRequestHeader(param: RequestParam) {
        _state.value = state.value.modifyHeaderSocket(param)
    }

    private fun deleteRequestParam(index: Int) {
        val updatedParams = state.value.paramsVo.params.remove(index)
        _state.value = state.value.copy(
            urlVo = state.value.urlVo.copy(
                url = buildUrlWithParams(state.value.urlVo.url, updatedParams)
            ),
            paramsVo = state.value.paramsVo.copy(params = updatedParams)
        )
    }

    private fun deleteRequestHeader(index: Int) {
        _state.value = state.value.deleteHeaderSocket(index)
    }

    private fun getRequestParams(url: String): List<RequestParam> {
        return (
                url.substringAfter("?", "")
                    .split("&")
                    .filter { it.contains("=") }
                    .mapIndexed { index, text ->
                        val (key, value) = text.split("=")
                        RequestParam(isChecked = true, index = index, key = key, value = value)
                    } + state.value.paramsVo.params.filter { !it.isChecked }
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