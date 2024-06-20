package data.source.remote

import domain.model.RequestMethod
import domain.model.RequestResult
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse

interface RequestRemoteSource {
    suspend fun doRequest(requestMethod: RequestMethod, url: String): Result<RequestResult>
}

class RequestRemoteSourceImpl(private val mapper: RemoteMapper) : RequestRemoteSource {
    override suspend fun doRequest(
        requestMethod: RequestMethod,
        url: String
    ): Result<RequestResult> = runCatching {
        val response: HttpResponse = ApiClient.client.request(url) {
            method = mapper.getHttpMethod(requestMethod)
        }

        val result = mapper.toRequestResult(response)
        return Result.success(result)
    }
}