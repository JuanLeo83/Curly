package data.repository

import data.source.remote.RequestRemoteSource
import domain.model.RequestParams
import domain.model.ResponseModel
import domain.repository.RequestRepository

class RequestRepositoryImpl(private val source: RequestRemoteSource) : RequestRepository {
    override suspend fun doRequest(params: RequestParams): Result<ResponseModel> {
        return source.doRequest(params)
    }
}