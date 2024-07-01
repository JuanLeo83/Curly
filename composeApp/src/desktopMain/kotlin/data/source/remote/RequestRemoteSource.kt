package data.source.remote

import domain.model.RequestParams
import domain.model.ResponseModel
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse

interface RequestRemoteSource {
    suspend fun doRequest(requestParams: RequestParams): Result<ResponseModel>
}

class RequestRemoteSourceImpl(private val mapper: RemoteMapper) : RequestRemoteSource {
    override suspend fun doRequest(
        requestParams: RequestParams): Result<ResponseModel> = runCatching {
        val response: HttpResponse = ApiClient.client.request(requestParams.url) {
            method = mapper.getHttpMethod(requestParams.method)
            mapper.addHeaders(this, requestParams.headers)
        }

        val result = mapper.toRequestResult(response)
        return Result.success(result)
    }
}