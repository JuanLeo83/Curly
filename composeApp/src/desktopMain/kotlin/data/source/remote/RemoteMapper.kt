package data.source.remote

import domain.model.BodyType
import domain.model.RequestMethod
import domain.model.RequestResult
import io.ktor.client.call.body
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
            responseTime = response.responseTime.timestamp - response.requestTime.timestamp,
            size = response.body<ByteArray>().size.toDouble(),
            type = getBodyType(response),
            body = response.bodyAsText()
        )
    }

    private fun getBodyType(response: HttpResponse): BodyType {
        val contentType = response.headers[CONTENT_TYPE]
        return when {
            contentType?.contains(JSON) == true -> BodyType.JSON
            contentType?.contains(HTML) == true -> BodyType.HTML
            contentType?.contains(APP_XML) == true ||
            contentType?.contains(TEXT_XML) == true -> BodyType.XML
            contentType?.contains(TEXT) == true -> BodyType.TEXT
            else -> BodyType.TEXT
        }
    }

    private companion object {
        const val CONTENT_TYPE = "Content-Type"
        const val JSON = "application/json"
        const val HTML = "text/html"
        const val APP_XML = "application/xml"
        const val TEXT_XML = "text/xml"
        const val TEXT = "text/plain"
    }

}