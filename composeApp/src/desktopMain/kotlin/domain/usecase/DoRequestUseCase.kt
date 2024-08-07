package domain.usecase

import domain.model.RequestParams
import domain.model.ResponseModel
import domain.repository.RequestRepository

class DoRequestUseCase(private val repository: RequestRepository) {
    suspend operator fun invoke(params: RequestParams): Result<ResponseModel> {
        return repository.doRequest(params)
    }
}