package presentation.screen.request

import domain.model.RequestParams
import domain.model.RequestResult

class RequestScreenMapper {

    internal fun mapToRequestParams(state: RequestScreenState): RequestParams {
        return RequestParams(
            method = state.method,
            url = state.url
        )
    }

    internal fun mapToResponseData(result: RequestResult): ResponseData {
        return ResponseData(
            statusCode = result.statusCode.toString(),
            responseTime = "${result.responseTime} $MILLIS",
            size = result.size.formatSize(),
            body = result.body
        )
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

    private companion object {
        const val MILLIS = "ms"
        const val BYTE = "B"
        const val KB_FORMAT = "%.2f KB"
        const val MB_FORMAT = "%.2f MB"
        const val GB_FORMAT = "%.2f GB"
        const val SIZE = 1024
    }

}