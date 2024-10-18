package presentation.screen.request

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.model.RequestMethod
import domain.model.ResponseModel
import domain.usecase.DoRequestUseCase
import extension.list.modify
import extension.list.remove
import extension.list.sortRequestParams
import kotlinx.coroutines.launch

class RequestScreenModel(
//    private val createConfigDirectoryUseCase: CreateConfigDirectoryUseCase,
//    private val loadCurrentThemeUseCase: LoadCurrentThemeUseCase,
    private val doRequestUseCase: DoRequestUseCase,
    private val mapper: RequestScreenMapper,
//    private val themeMapper: ThemeMapper
) : StateScreenModel<RequestScreenState>(RequestScreenState()) {

//    init {
//        screenModelScope.launch {
//            createConfigDirectoryUseCase().fold(
//                onSuccess = {
//                    loadCurrentThemeUseCase().fold(
//                        onSuccess = {
//                            theme = themeMapper.mapToTheme(it)
//                            mutableState.value = state.value.copy(initialized = true)
//                        },
//                        onFailure = { println("Error loading current theme: $it") }
//                    )
//                },
//                onFailure = { println("Error creating config directory: $it") }
//            )
//        }
//    }

    fun setUrl(url: String) {
        mutableState.value = state.value.copy(
            urlVo = state.value.urlVo.copy(url = url),
            paramsVo = state.value.paramsVo.copy(params = getRequestParams(url))
        )
    }

    fun setRequestMethod(method: RequestMethod) {
        mutableState.value = state.value.setMethod(method)
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
        mutableState.value = state.value.setBodyType(bodyType)
    }

    fun setRequestBody(value: String) {
        mutableState.value = state.value.setBody(value)
    }

    fun setAuthorizationType(authorizationType: String) {
        val authType = mapper.mapAuthorizationTypeByName(authorizationType)
        mutableState.value = state.value.setAuthorizationType(authType)
    }

    fun onBasicAuthUserNameChange(userName: String) {
        mutableState.value = state.value.setBasicUserName(userName)
    }

    fun onBasicAuthPasswordChange(password: String) {
        mutableState.value = state.value.setBasicPassword(password)
    }

    fun onBearerTokenChange(token: String) {
        mutableState.value = state.value.setBearerToken(token)
    }

    fun onApiKeyAddToSelected(optionSelected: String) {
        val addTo = mapper.mapAddToByName(optionSelected)
        mutableState.value = state.value.setApiKeyAddTo(addTo)
    }

    fun onApiKeyChange(apiKey: String) {
        mutableState.value = state.value.setApiKey(apiKey)
    }

    fun onApiKeyValueChange(apiKeyValue: String) {
        mutableState.value = state.value.setApiKeyValue(apiKeyValue)
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
        mutableState.value = state.value.addParamSocket()
    }

    private fun addRequestHeader() {
        mutableState.value = state.value.addHeaderSocket()
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
        mutableState.value = state.value.modifyHeaderSocket(param)
    }

    private fun deleteRequestParam(index: Int) {
        val updatedParams = state.value.paramsVo.params.remove(index)
        mutableState.value = state.value.copy(
            urlVo = state.value.urlVo.copy(
                url = buildUrlWithParams(state.value.urlVo.url, updatedParams)
            ),
            paramsVo = state.value.paramsVo.copy(params = updatedParams)
        )
    }

    private fun deleteRequestHeader(index: Int) {
        mutableState.value = state.value.deleteHeaderSocket(index)
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