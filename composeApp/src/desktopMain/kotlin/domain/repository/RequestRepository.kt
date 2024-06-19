package domain.repository

import domain.model.RequestParams
import domain.model.RequestResult

interface RequestRepository {
    suspend fun doRequest(params: RequestParams): Result<RequestResult>
}