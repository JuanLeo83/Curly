package data.source.remote

import domain.model.RequestMethod
import domain.model.RequestResult
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod

class RemoteMapper {

    internal fun getHttpMethod(method: RequestMethod): HttpMethod {
        return when (method) {
            RequestMethod.GET -> HttpMethod.Get
            RequestMethod.POST -> HttpMethod.Post
            RequestMethod.PUT -> HttpMethod.Put
            RequestMethod.PATCH -> HttpMethod.Patch
            RequestMethod.DELETE -> HttpMethod.Delete
            RequestMethod.HEAD -> HttpMethod.Head
            RequestMethod.OPTIONS -> HttpMethod.Options
        }
    }

    internal suspend fun toRequestResult(response: HttpResponse): RequestResult {
        return RequestResult(
            statusCode = response.status.value,
            responseTime = response.responseTime.timestamp,
            size = 1548.0,
            body = response.bodyAsText()
        )
    }

}