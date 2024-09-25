package presentation.screen.request

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Document
import com.fleeksoft.ksoup.parser.Parser
import domain.model.BodyType
import domain.model.RequestHeader
import domain.model.RequestParams
import domain.model.ResponseHeader
import domain.model.ResponseModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RequestScreenMapper {

    private val json = Json { prettyPrint = true }

    internal fun mapToRequestParams(state: RequestScreenState): RequestParams {
        return RequestParams(
            method = state.urlVo.method,
            url = completeUrlIfNeeded(state.urlVo.url),
            headers = mapHeadersToRequest(state.headersVo.params),
            bodyType = state.bodyVo.optionSelected,
            body = state.bodyVo.value
        )
    }

    internal fun mapToResponseData(result: ResponseModel): ResponseData {
        return ResponseData(
            status = "${result.statusCode} ${result.statusDescription}",
            responseTime = "${result.responseTime} $MILLIS",
            size = result.size.formatSize(),
            type = result.type,
            rawBody = result.body,
            body = formatBody(result),
            headers = mapHeaders(result.headers)
        )
    }

    private fun completeUrlIfNeeded(url: String): String =
        if (url.startsWith(HTTP)) url else "${DEFAULT_PROTOCOL}$url"

    private fun mapHeadersToRequest(headerParams: List<RequestParam>): List<RequestHeader> {
        return headerParams.mapNotNull { param ->
            if (param.isChecked) {
                RequestHeader(param.key, param.value)
            } else null
        }
    }

    private fun Double.formatSize(): String {
        val kb = SIZE
        val mb = kb * SIZE
        val gb = mb * SIZE

        return when {
            this < kb -> "$this $BYTE"
            this < mb -> String.format(KB_FORMAT, this / kb)
            this < gb -> String.format(MB_FORMAT, this / mb)
            else -> String.format(GB_FORMAT, this / gb)
        }
    }

    private fun formatBody(result: ResponseModel): String =
        when (result.type) {
            BodyType.JSON -> formatJson(result.body)
            BodyType.XML -> formatXml(result.body)
            BodyType.HTML -> formatHtml(result.body)
            BodyType.TEXT -> result.body
            else -> ""
        }

    private fun formatJson(body: String): String {
        return json.encodeToString(Json.parseToJsonElement(body))
    }

    private fun formatXml(body: String): String {
        return Ksoup.parse(
            html = body,
            parser = Parser.xmlParser()
        ).outputSettings(Document.OutputSettings(indentAmount = 4)).toString()
    }

    private fun formatHtml(body: String): String {
        return Ksoup.parse(body)
            .outputSettings(Document.OutputSettings(indentAmount = 4))
            .toString()
    }

    private fun mapHeaders(headers: List<ResponseHeader>): Map<String, String> {
        val mappedHeaders = mutableMapOf<String, String>()
        headers.forEach {
            mappedHeaders[it.key] = joinHeaders(it.value)
        }
        return mappedHeaders
    }

    private fun joinHeaders(headers: List<String>): String {
        var joinedHeaders: String = ""
        headers.forEach {
            joinedHeaders += "$it, "
        }
        return joinedHeaders.substring(0, joinedHeaders.length - 2)
    }

    private companion object {
        const val MILLIS = "ms"
        const val BYTE = "B"
        const val KB_FORMAT = "%.2f KB"
        const val MB_FORMAT = "%.2f MB"
        const val GB_FORMAT = "%.2f GB"
        const val SIZE = 1024
        const val HTTP = "http"
        const val DEFAULT_PROTOCOL = "$HTTP://"
    }

}