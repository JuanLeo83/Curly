package domain.usecase

import domain.model.RequestParams
import domain.model.RequestResult
import domain.repository.RequestRepository

class DoRequestUseCase(private val repository: RequestRepository) {
    suspend operator fun invoke(params: RequestParams): Result<RequestResult> {
        return repository.doRequest(params)
    }
}