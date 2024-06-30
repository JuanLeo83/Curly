package data.source.remote

import domain.model.BodyType
import domain.model.RequestHeader
import domain.model.RequestMethod
import domain.model.RequestResult
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
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
            size = getSize(response),
            type = getBodyType(response),
            body = response.bodyAsText()
        )
    }

    internal fun addHeaders(requestBuilder: HttpRequestBuilder, headers: List<RequestHeader>) {
        headers.forEach { header ->
            requestBuilder.headers.append(header.key, header.value)
        }

        if (requestBuilder.headers[HttpHeaders.Accept].isNullOrEmpty()) {
            requestBuilder.headers.append(HttpHeaders.Accept, HEADER_ACCEPT_DEFAULT)
        }

        if (requestBuilder.headers[HttpHeaders.ContentEncoding].isNullOrEmpty()) {
            requestBuilder.headers.append(HttpHeaders.ContentEncoding, HEADER_CONTENT_ENCODING_DEFAULT)
        }

        if (requestBuilder.headers[HttpHeaders.ContentType].isNullOrEmpty()) {
            requestBuilder.headers.append(HttpHeaders.ContentType, HEADER_CONTENT_TYPE_DEFAULT)
        }
    }

    private suspend fun getSize(response: HttpResponse): Double {
        val headersSize = response.headers.toString().toByteArray().size.toDouble()
        val bodySize = response.headers[CONTENT_LENGTH]?.toDouble()
            ?: response.body<ByteArray>().size.toDouble()

        return headersSize + bodySize
    }

    private fun getBodyType(response: HttpResponse): BodyType {
        val contentType = response.headers[HttpHeaders.ContentType]
        return when {
            contentType?.contains(JSON) == true -> BodyType.JSON
            contentType?.contains(HTML) == true -> BodyType.HTML
            contentType?.contains(APP_XML) == true || contentType?.contains(TEXT_XML) == true ->
                BodyType.XML

            contentType?.contains(TEXT) == true -> BodyType.TEXT
            else -> BodyType.TEXT
        }
    }

    private companion object {
        const val CONTENT_LENGTH = "Content-Length"
        const val JSON = "application/json"
        const val HTML = "text/html"
        const val APP_XML = "application/xml"
        const val TEXT_XML = "text/xml"
        const val TEXT = "text/plain"

        const val HEADER_ACCEPT_DEFAULT = "*/*"
        const val HEADER_CONTENT_ENCODING_DEFAULT = "gzip, deflate, br"
        const val HEADER_CONTENT_TYPE_DEFAULT = JSON
    }

}