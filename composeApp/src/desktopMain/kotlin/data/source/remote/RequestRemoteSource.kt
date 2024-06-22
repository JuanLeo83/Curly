package data.source.remote

import domain.model.RequestMethod
import domain.model.RequestResult
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders

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

            headers.append(HttpHeaders.Accept, HEADER_ACCEPT_DEFAULT)
            headers.append(HttpHeaders.ContentEncoding, HEADER_CONTENT_ENCODING_DEFAULT)
            headers.append(HttpHeaders.ContentType, HEADER_CONTENT_TYPE_DEFAULT)
        }

        val result = mapper.toRequestResult(response)
        return Result.success(result)
    }

    private companion object {
        const val HEADER_ACCEPT_DEFAULT = "*/*"
        const val HEADER_CONTENT_ENCODING_DEFAULT = "gzip, deflate, br"
        const val HEADER_CONTENT_TYPE_DEFAULT = "application/json"
    }
}