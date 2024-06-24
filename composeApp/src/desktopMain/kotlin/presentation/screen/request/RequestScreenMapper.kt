package presentation.screen.request

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Document
import com.fleeksoft.ksoup.parser.Parser
import domain.model.BodyType
import domain.model.RequestParams
import domain.model.RequestResult
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RequestScreenMapper {

    private val json = Json { prettyPrint = true }

    internal fun mapToRequestParams(state: RequestScreenState): RequestParams {
        return RequestParams(
            method = state.method,
            url = completeUrlIfNeeded(state.url)
        )
    }

    internal fun mapToResponseData(result: RequestResult): ResponseData {
        return ResponseData(
            statusCode = result.statusCode.toString(),
            responseTime = "${result.responseTime} $MILLIS",
            size = result.size.formatSize(),
            type = result.type,
            rawBody = result.body,
            body = formatBody(result)
        )
    }

    private fun completeUrlIfNeeded(url: String): String =
        if (url.startsWith(HTTP)) url else "${DEFAULT_PROTOCOL}$url"

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

    private fun formatBody(result: RequestResult): String =
        when (result.type) {
            BodyType.JSON -> formatJson(result.body)
            BodyType.XML -> formatXml(result.body)
            BodyType.HTML -> formatHtml(result.body)
            BodyType.TEXT -> result.body
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