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
import presentation.screen.request.component.request.authorization.vo.ApiKeyAddTo

class RequestScreenModel(
    private val doRequestUseCase: DoRequestUseCase,
    private val mapper: RequestScreenMapper
) : StateScreenModel<RequestScreenState>(RequestScreenState()) {

    fun setUrl(url: String) {
        mutableState.value = state.value.copy(
            urlVo = state.value.urlVo.copy(url = url),
            paramsVo = state.value.paramsVo.copy(params = getRequestParams(url))
        )
    }

    fun setRequestMethod(method: RequestMethod) {
        mutableState.value = state.value.copy(
            urlVo = state.value.urlVo.copy(method = method)
        )
    }

    fun sendRequest() = screenModelScope.launch {
        if (state.value.urlVo.url.isEmpty()) return@launch

        val params = mapper.mapToRequestParams(state.value)
        mutableState.value = state.value.copy(
            urlVo = state.value.urlVo.copy(url = params.url),
            isLoading = true
        )
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
            TableType.COOKIES,
            TableType.BODY,
            TableType.AUTHORIZATION -> Unit
        }
    }

    fun onValueChange(type: TableType, param: RequestParam) {
        when (type) {
            TableType.PARAMS -> modifyRequestParam(param)
            TableType.HEADERS -> modifyRequestHeader(param)
            TableType.COOKIES,
            TableType.BODY,
            TableType.AUTHORIZATION -> Unit
        }
    }

    fun deleteRow(type: TableType, index: Int) {
        when (type) {
            TableType.PARAMS -> deleteRequestParam(index)
            TableType.HEADERS -> deleteRequestHeader(index)
            TableType.COOKIES,
            TableType.BODY,
            TableType.AUTHORIZATION -> Unit
        }
    }

    fun setRequestBodyType(optionSelected: BodyType) {
        mutableState.value = state.value.copy(
            bodyVo = state.value.bodyVo.copy(optionSelected = optionSelected)
        )
    }

    fun setRequestBody(value: String) {
        mutableState.value = state.value.copy(
            bodyVo = state.value.bodyVo.copy(value = value)
        )
    }

    fun setAuthorizationType(authorizationType: AuthorizationType) {
        mutableState.value = state.value.copy(
            authVo = state.value.authVo.copy(
                optionSelected = authorizationType)
        )
    }

    fun onBasicAuthUserNameChange(userName: String) {
        mutableState.value = state.value.copy(
            authVo = state.value.authVo.copy(
                basic = state.value.authVo.basic.copy(userName = userName)
            )
        )
    }

    fun onBasicAuthPasswordChange(password: String) {
        mutableState.value = state.value.copy(
            authVo = state.value.authVo.copy(
                basic = state.value.authVo.basic.copy(password = password)
            )
        )
    }

    fun onBearerTokenChange(token: String) {
        mutableState.value = state.value.copy(
            authVo = state.value.authVo.copy(
                bearer = state.value.authVo.bearer.copy(token = token)
            )
        )
    }

    fun onApiKeyAddToSelected(optionSelected: ApiKeyAddTo) {
        mutableState.value = state.value.copy(
            authVo = state.value.authVo.copy(
                apiKey = state.value.authVo.apiKey.copy(optionSelected = optionSelected)
            )
        )
    }

    fun onApiKeyChange(apiKey: String) {
        mutableState.value = state.value.copy(
            authVo = state.value.authVo.copy(
                apiKey = state.value.authVo.apiKey.copy(key = apiKey)
            )
        )
    }

    fun onApiKeyValueChange(apiKeyValue: String) {
        mutableState.value = state.value.copy(
            authVo = state.value.authVo.copy(
                apiKey = state.value.authVo.apiKey.copy(value = apiKeyValue)
            )
        )
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
        mutableState.value = state.value.copy(
            paramsVo = state.value.paramsVo.copy(
                params = state.value.paramsVo.params.add(
                    RequestParam(index = state.value.paramsVo.params.size)
                )
            )
        )
    }

    private fun addRequestHeader() {
        mutableState.value = state.value.copy(
            headersVo = state.value.headersVo.copy(
                params = state.value.headersVo.params.add(
                    RequestParam(index = state.value.headersVo.params.size)
                )
            )
        )
    }

    private fun modifyRequestParam(param: RequestParam) {
        val updatedParams = state.value.paramsVo.params.modify(param, param.index)
        mutableState.value = state.value.copy(
            urlVo = state.value.urlVo.copy(
                url = buildUrlWithParams(state.value.urlVo.url, updatedParams)
            ),
            paramsVo = state.value.paramsVo.copy(params = updatedParams)
        )
    }

    private fun modifyRequestHeader(param: RequestParam) {
        mutableState.value = state.value.copy(
            headersVo = state.value.headersVo.copy(
                params = state.value.headersVo.params.modify(param, param.index)
            )
        )
    }

    private fun deleteRequestParam(index: Int) {
        val updatedParams = state.value.paramsVo.params.remove(index)
        mutableState.value = state.value.copy(
            urlVo = state.value.urlVo.copy(
                url = buildUrlWithParams(state.value.urlVo.url, updatedParams)
            ),
            paramsVo = state.value.paramsVo.copy(
                params = updatedParams
            )
        )
    }

    private fun deleteRequestHeader(index: Int) {
        mutableState.value = state.value.copy(
            headersVo = state.value.headersVo.copy(
                params = state.value.headersVo.params.remove(index)
            )
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