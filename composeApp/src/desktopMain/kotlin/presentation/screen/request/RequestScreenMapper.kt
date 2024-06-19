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
        return when {
            this < ONE -> "${(this * SIZE).toInt()} $KB"
            this < SIZE -> "${this.toInt()} $KB"
            else -> "${this / SIZE} $MB"
        }
    }

    private companion object {
        const val MILLIS = "ms"
        const val KB = "KB"
        const val MB = "MB"
        const val ONE = 1
        const val SIZE = 1024
    }

}