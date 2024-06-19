package presentation.screen.request

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.model.RequestMethod
import domain.model.RequestResult
import domain.usecase.DoRequestUseCase
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
        val params = mapper.mapToRequestParams(state.value)
        doRequestUseCase(params).fold(
            onSuccess = ::requestSuccessHandler,
            onFailure = ::requestFailureHandler
        )
    }

    private fun requestSuccessHandler(result: RequestResult) {
        mutableState.value = state.value.copy(
            responseData = mapper.mapToResponseData(result),
            errorMessage = ""
        )
    }

    private fun requestFailureHandler(throwable: Throwable) {
        mutableState.value = state.value.copy(
            responseData = null,
            errorMessage = throwable.message ?: ""
        )
    }

}