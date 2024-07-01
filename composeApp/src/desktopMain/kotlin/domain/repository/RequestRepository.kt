package domain.repository

import domain.model.RequestParams
import domain.model.ResponseModel

interface RequestRepository {
    suspend fun doRequest(params: RequestParams): Result<ResponseModel>
}