package presentation.screen.request

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.usecase.SampleUseCase
import kotlinx.coroutines.launch

class RequestScreenModel(
    private val sampleUseCase: SampleUseCase
) : StateScreenModel<RequestScreenState>(RequestScreenState()) {

    fun getMessage() = screenModelScope.launch {
        sampleUseCase().fold(
            onSuccess = { mutableState.value = state.value.copy(message = it.message) },
            onFailure = { mutableState.value = state.value.copy(errorMessage = "Algo ha ido mal") }
        )
    }

}